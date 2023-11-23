package tech.coner.trailer.cli

import com.github.ajalt.clikt.core.context
import java.nio.file.Path
import kotlin.io.path.createDirectory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import tech.coner.trailer.cli.clikt.StringBuilderConsole
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.di.Invocation
import tech.coner.trailer.cli.util.IntegrationTestAppArgumentBuilder
import tech.coner.trailer.cli.util.SubcommandArgumentsFactory

class ConerTrailerCliIT {

    lateinit var invocation: Invocation
    lateinit var command: RootCommand

    @TempDir lateinit var testDir: Path
    lateinit var configDir: Path
    lateinit var snoozleDir: Path
    lateinit var crispyFishDir: Path

    lateinit var argumentsFactory: SubcommandArgumentsFactory
    lateinit var appArgumentBuilder: IntegrationTestAppArgumentBuilder
    lateinit var testConsole: StringBuilderConsole


    @BeforeEach
    fun before() {
        configDir = testDir.resolve("config").apply { createDirectory() }
        snoozleDir = testDir.resolve("snoozle").apply { createDirectory() }
        crispyFishDir = testDir.resolve("crispy-fish").apply { createDirectory() }
        argumentsFactory = SubcommandArgumentsFactory(
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
        appArgumentBuilder = IntegrationTestAppArgumentBuilder(
            configDir = configDir,
            snoozleDir = snoozleDir,
            crispyFishDir = crispyFishDir
        )
        testConsole = StringBuilderConsole()
        invocation = ConerTrailerCli.createInvocation()
        command = ConerTrailerCli.createRootCommand(invocation)
            .context {
                console = testConsole
            }
    }

    private fun args(vararg args: String) = appArgumentBuilder.build(*args)
}
