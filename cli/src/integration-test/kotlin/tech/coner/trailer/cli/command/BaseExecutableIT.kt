package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.context
import java.nio.file.Path
import kotlin.io.path.createDirectory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.cli.ConerTrailerCli
import tech.coner.trailer.cli.clikt.StringBuilderConsole
import tech.coner.trailer.cli.util.ConerTrailerCliProcessExecutor
import tech.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import tech.coner.trailer.cli.util.NativeImageCommandArrayFactory
import tech.coner.trailer.cli.util.ProcessOutcome
import tech.coner.trailer.cli.util.ShadedJarCommandArrayFactory
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

    protected fun arrange(fn: IntegrationTestAppArgumentBuilder.() -> Array<String>) {
        ConerTrailerCli.createCommands()
            .context {
                console = StringBuilderConsole()
            }
            .parse(fn(createAppArgumentBuilder()))
    }

    protected fun testCommand(fn: ConerTrailerCliProcessExecutor.() -> Process): ProcessOutcome {
        return testCommandAsync(fn)
            .awaitOutcome()
    }

    protected fun testCommandAsync(fn: ConerTrailerCliProcessExecutor.() -> Process): Process {
        return fn(
            ConerTrailerCliProcessExecutor(
                processCommandArrayFactory = when (System.getProperty("coner-trailer-cli.argument-factory", "shaded-jar")) {
                    "shaded-jar" -> ShadedJarCommandArrayFactory()
                    "native-image" -> NativeImageCommandArrayFactory()
                    else -> throw IllegalStateException("Unknown argument factory")
                },
                appArgumentBuilder = createAppArgumentBuilder()
            )
        )
    }

    private fun createAppArgumentBuilder() = IntegrationTestAppArgumentBuilder(
        configDir = configDir,
        snoozleDir = snoozleDir,
        crispyFishDir = crispyFishDir
    )
}