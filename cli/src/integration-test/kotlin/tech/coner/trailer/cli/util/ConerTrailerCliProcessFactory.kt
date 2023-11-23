package tech.coner.trailer.cli.util

import java.nio.file.Path
import tech.coner.trailer.Club
import tech.coner.trailer.Event
import tech.coner.trailer.Participant

class ConerTrailerCliProcessFactory(
    private val configDir: Path,
    private val processCommandArrayFactory: ProcessCommandArrayFactory,
    private val subcommandArgumentsFactory: SubcommandArgumentsFactory
) {

    fun root(help: Boolean? = null): Process {
        return exec(CommandParameters.builder(configDir) {
            this.help = help
        })
    }

    fun clubSet(club: Club): Process {
        return execSubcommand { clubSet(club) }
    }

    fun configDatabaseAdd(databaseName: String): Process {
        return execSubcommand { configDatabaseAdd(databaseName) }
    }

    fun eventAddCrispyFish(event: Event, crispyFishEventControlFile: Path, crispyFishClassDefinitionFile: Path): Process {
        return execSubcommand { eventAddCrispyFish(event = event, crispyFishEventControlFile = crispyFishEventControlFile, crispyFishClassDefinitionFile = crispyFishClassDefinitionFile) }
    }

    fun eventCheck(event: Event): Process {
        return execSubcommand { eventCheck(event) }
    }

    fun eventCrispyFishPersonMapAdd(event: Event, participant: Participant): Process {
        return execSubcommand { eventCrispyFishPersonMapAdd(event, participant) }
    }

    fun eventParticipantList(event: Event): Process {
        return execSubcommand { eventParticipantList(event) }
    }

    fun eventRunList(event: Event): Process {
        return execSubcommand { eventRunList(event) }
    }

    fun motorsportregMemberList(
        motorsportRegUsername: String,
        motorsportRegPassword: String,
        motorsportRegOrganizationId: String
    ): Process {
        return execSubcommand(
            motorsportRegUsername = motorsportRegUsername,
            motorsportRegPassword = motorsportRegPassword,
            motorsportRegOrganizationId = motorsportRegOrganizationId
        ) { motorsportregMemberList() }
    }

    private fun execSubcommand(
        motorsportRegUsername: String? = null,
        motorsportRegPassword: String? = null,
        motorsportRegOrganizationId: String? = null,
        fn: (SubcommandArgumentsFactory).() -> SubcommandArguments
    ): Process {
        return exec(
            CommandParameters.builder(configDir) {
                this.motorsportRegUsername = motorsportRegUsername
                this.motorsportRegPassword = motorsportRegPassword
                this.motorsportRegOrganizationId = motorsportRegOrganizationId
                subcommandArguments = fn.invoke(subcommandArgumentsFactory)
            }
        )
    }

    private fun exec(args: CommandParameters): Process {
        val commandArray = buildList {
            addAll(processCommandArrayFactory.build())
            if (args.verbose == true) add("-v")
            args.format?.also { addAll(listOf("--format", it)) }
            if (args.help == true) add("--help")
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