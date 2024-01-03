package tech.coner.trailer.app.admin.command

import com.github.ajalt.clikt.testing.CliktCommandTestResult
import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.app.admin.ConerTrailerAdmin
import tech.coner.trailer.app.admin.util.*
import java.nio.file.Path
import kotlin.io.path.createDirectory

abstract class BaseExecutableIT {

    @TempDir
    lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
    }

    protected fun arrange(fn: SubcommandArgumentsFactory.() -> SubcommandArguments): CliktCommandTestResult {
        val invocation = ConerTrailerAdmin.createInvocation()
        val arguments = buildList {
            addAll(listOf("--config-dir", "$configDir"))
            addAll(fn(createSubcommandArgumentsFactory()).args)
        }
        return ConerTrailerAdmin.createRootCommand(invocation)
            .test(arguments)
    }

    protected fun testCommand(fn: ConerTrailerCliProcessFactory.() -> Process): ProcessOutcome {
        return testCommandAsync(fn)
            .awaitOutcome()
    }

    protected fun testCommandAsync(fn: ConerTrailerCliProcessFactory.() -> Process): Process {
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

    private fun createProcessCommandArrayFactory() =
        when (System.getProperty("coner-trailer-cli.argument-factory", "shaded-jar")) {
            "shaded-jar" -> ShadedJarCommandArrayFactory()
            else -> throw IllegalStateException("Unknown argument factory")
        }
}