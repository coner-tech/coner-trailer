package tech.coner.trailer.cli.util

import java.nio.file.Path
import java.time.LocalDate
import java.util.UUID
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import tech.coner.trailer.Club
import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Person
import tech.coner.trailer.Policy
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.di.Format


class ConerTrailerCliProcessExecutor(
    private val processCommandArrayFactory: ProcessCommandArrayFactory,
    private val appArgumentBuilder: IntegrationTestAppArgumentBuilder
) {

    private val logger: Logger = LoggerFactory.getLogger(ConerTrailerCliProcessExecutor::class.java)

    fun exec(vararg args: String, environment: Array<String> = emptyArray()): Process {
        val commandArray = processCommandArrayFactory.build()
            .toMutableList()
            .apply {
                addAll(appArgumentBuilder.build(*args))
            }
            .toTypedArray()
        logger.info { commandArray.joinToString(" ") }
        val useEnvironment = buildList {
            addAll(environment)
            add("TMP=${System.getProperty("java.io.tmpdir")}")
        }.toTypedArray()
        return Runtime.getRuntime().exec(commandArray, useEnvironment)
    }

    fun clubSet(club: Club): Process {
        return exec(
            *appArgumentBuilder.clubSet(club)
        )
    }

    fun configDatabaseAdd(databaseName: String): Process {
        return exec(
            *appArgumentBuilder.configDatabaseAdd(databaseName)
        )
    }

    fun policyAdd(policy: Policy): Process = exec(
        *appArgumentBuilder.policyAdd(policy)
    )

    fun eventAddCrispyFish(
        event: Event,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path
    ): Process {
        return exec(
            *appArgumentBuilder.eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = crispyFishEventControlFile,
                crispyFishClassDefinitionFile = crispyFishClassDefinitionFile
            )
        )
    }

    fun eventSet(
        eventId: UUID,
        name: String? = null,
        date: LocalDate? = null,
        lifecycle: Event.Lifecycle? = null,
        setCrispyFishMetadata: Event.CrispyFishMetadata? = null,
        unsetCrispyFishMetadata: Unit? = null
    ): Process {
        return exec(
            *appArgumentBuilder.eventSet(
                eventId = eventId,
                name = name,
                date = date,
                lifecycle = lifecycle,
                setCrispyFishMetadata = setCrispyFishMetadata,
                unsetCrispyFishMetadata = unsetCrispyFishMetadata
            )
        )
    }

    fun personAdd(person: Person): Process {
        return exec(*appArgumentBuilder.personAdd(person))
    }

    fun eventCrispyFishPersonMapAdd(
        event: Event,
        participant: Participant
    ): Process {
        return exec(
            *appArgumentBuilder.eventCrispyFishPersonMapAdd(
                event = event,
                participant = participant
            )
        )
    }

    fun eventResults(
        event: Event,
        type: EventResultsType,
        format: Format,
        output: String
    ): Process = exec(
        *appArgumentBuilder.eventResults(
            event = event,
            type = type,
            format = format,
            output = output
        )
    )

    fun webappCompetition(
        port: Int? = null,
        exploratory: Boolean = false
    ): Process = exec(
        *appArgumentBuilder.webappCompetition(
            port = port,
            exploratory = exploratory
        )
    )

}