package tech.coner.trailer.cli.util

import java.nio.file.Path
import tech.coner.trailer.Club
import tech.coner.trailer.Event

class ConerTrailerCliProcessFactory(
    private val configDir: Path,
    private val processCommandArrayFactory: ProcessCommandArrayFactory,
    private val subcommandArgumentsFactory: SubcommandArgumentsFactory
) {

    fun clubSet(club: Club): Process {
        return execSubcommand { clubSet(club) }
    }

    fun eventAddCrispyFish(event: Event, crispyFishEventControlFile: Path, crispyFishClassDefinitionFile: Path): Process {
        return execSubcommand { eventAddCrispyFish(event = event, crispyFishEventControlFile = crispyFishEventControlFile, crispyFishClassDefinitionFile = crispyFishClassDefinitionFile) }
    }

    private fun execSubcommand(fn: (SubcommandArgumentsFactory).() -> SubcommandArguments): Process {
        return exec(
            CommandParameters.builder(configDir) {
                subcommandArguments = fn.invoke(subcommandArgumentsFactory)
            }
        )
    }

    private fun exec(args: CommandParameters): Process {
        val commandArray = buildList {
            addAll(processCommandArrayFactory.build())
            if (args.verbose == true) add("-v")
            args.format?.also { addAll(listOf("--format", it)) }
            args.subcommandArguments?.also { addAll(it.args) }
        }
            .toTypedArray()
        val environmentArray = buildList {
            add("TMP=${System.getProperty("java.io.tmpdir")}")
            add("CONER_TRAILER_CONFIG_DIR=${args.configDir}")
            args.database?.also { add("CONER_TRAILER_DATABASE=$it") }
            args.motorsportRegUsername?.also { add("MOTORSPORTREG_USERNAME=$it") }
            args.motorsportRegPassword?.also { add("MOTORSPORTREG_PASSWORD=$it") }
            args.motorsportRegOrganizationId?.also { add("MOTORSPORTREG_ORGANIZATION_ID=$it") }
            args.subcommandEnvironmentVariables?.also { addAll(it.envVars) }
        }
            .toTypedArray()
        return Runtime.getRuntime().exec(commandArray, environmentArray)
    }
}