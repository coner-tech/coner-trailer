package tech.coner.trailer.app.admin.util

import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Person
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.di.Format
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

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

    fun eventAddCrispyFish(
        event: Event,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path
    ): Process {
        return execSubcommand {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = crispyFishEventControlFile,
                crispyFishClassDefinitionFile = crispyFishClassDefinitionFile
            )
        }
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

    fun eventResults(
        event: Event,
        eventResultsType: EventResultsType,
        output: String? = null,
        format: Format? = null,
    ): Process {
        return execSubcommand(format = format) {
            eventResults(event, eventResultsType, output)
        }
    }

    fun eventRunList(event: Event): Process {
        return execSubcommand { eventRunList(event) }
    }

    fun eventSet(
        eventId: UUID,
        name: String? = null,
        date: LocalDate? = null,
        lifecycle: Event.Lifecycle? = null,
        setCrispyFishMetadata: Event.CrispyFishMetadata? = null,
        unsetCrispyFishMetadata: Unit? = null
    ): Process {
        return execSubcommand {
            eventSet(
                eventId = eventId,
                name = name,
                date = date,
                lifecycle = lifecycle,
                setCrispyFishMetadata = setCrispyFishMetadata,
                unsetCrispyFishMetadata = unsetCrispyFishMetadata
            )
        }
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

    fun personDelete(
        person: Person
    ): Process {
        return execSubcommand { personDelete(person) }
    }

    fun webappCompetition(
        port: Int? = null,
        exploratory: Boolean? = null
    ): Process {
        return execSubcommand { webappCompetition(port, exploratory) }
    }

    private fun execSubcommand(
        format: Format? = null,
        motorsportRegUsername: String? = null,
        motorsportRegPassword: String? = null,
        motorsportRegOrganizationId: String? = null,
        fn: (SubcommandArgumentsFactory).() -> SubcommandArguments
    ): Process {
        return exec(
            CommandParameters.builder(configDir) {
                this.format = format
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
            args.format?.also { addAll(listOf("--format", it.name.lowercase())) }
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