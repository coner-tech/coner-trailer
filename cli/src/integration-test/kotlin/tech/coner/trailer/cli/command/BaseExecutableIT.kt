package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.context
import java.nio.file.Path
import kotlin.io.path.createDirectory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.cli.ConerTrailerCli
import tech.coner.trailer.cli.clikt.StringBuilderConsole
import tech.coner.trailer.cli.util.ConerTrailerCliProcessExecutor
import tech.coner.trailer.cli.util.ConerTrailerCliProcessFactory
import tech.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import tech.coner.trailer.cli.util.NativeImageCommandArrayFactory
import tech.coner.trailer.cli.util.ProcessOutcome
import tech.coner.trailer.cli.util.ShadedJarCommandArrayFactory
import tech.coner.trailer.cli.util.SubcommandArguments
import tech.coner.trailer.cli.util.SubcommandArgumentsFactory
import tech.coner.trailer.cli.util.awaitOutcome

abstract class BaseExecutableIT {

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
    }

    @Deprecated("Uses non-type-safe array of strings argument builder")
    protected fun arrange(fn: IntegrationTestAppArgumentBuilder.() -> Array<String>) {
        val invocation = ConerTrailerCli.createInvocation()
        ConerTrailerCli.createRootCommand(invocation)
            .context {
                console = StringBuilderConsole()
            }
            .parse(fn(createAppArgumentBuilder()))
    }

    protected fun newArrange(fn: SubcommandArgumentsFactory.() -> SubcommandArguments) {
        val invocation = ConerTrailerCli.createInvocation()
        val arguments = buildList {
            addAll(listOf("--config-dir", "$configDir"))
            addAll(fn(createSubcommandArgumentsFactory()).args)
        }
        ConerTrailerCli.createRootCommand(invocation)
            .context {
                console = StringBuilderConsole()
            }
            .parse(arguments)
    }

    @Deprecated("Uses non-type-safe array of strings argument builder")
    protected fun testCommand(fn: ConerTrailerCliProcessExecutor.() -> Process): ProcessOutcome {
        return testCommandAsync(fn)
            .awaitOutcome()
    }

    protected fun testCommandAsync(fn: ConerTrailerCliProcessExecutor.() -> Process): Process {
        return fn(
            ConerTrailerCliProcessExecutor(
                processCommandArrayFactory = createProcessCommandArrayFactory(),
                appArgumentBuilder = createAppArgumentBuilder()
            )
        )
    }

    protected fun newTestCommand(fn: ConerTrailerCliProcessFactory.() -> Process): ProcessOutcome {
        return newTestCommandAsync(fn)
            .awaitOutcome()
    }

    protected fun newTestCommandAsync(fn: ConerTrailerCliProcessFactory.() -> Process): Process {
        return fn(
            ConerTrailerCliProcessFactory(
                configDir = configDir,
                processCommandArrayFactory = createProcessCommandArrayFactory(),
                subcommandArgumentsFactory = createSubcommandArgumentsFactory()
            )
        )
    }

    private fun createSubcommandArgumentsFactory() = SubcommandArgumentsFactory(
        snoozleDir = snoozleDir,
        crispyFishDir = crispyFishDir
    )

    private fun createProcessCommandArrayFactory() = when (System.getProperty("coner-trailer-cli.argument-factory", "shaded-jar")) {
        "shaded-jar" -> ShadedJarCommandArrayFactory()
        "native-image" -> NativeImageCommandArrayFactory()
        else -> throw IllegalStateException("Unknown argument factory")
    }

    private fun createAppArgumentBuilder() = IntegrationTestAppArgumentBuilder(
        configDir = configDir,
        snoozleDir = snoozleDir,
        crispyFishDir = crispyFishDir
    )
}