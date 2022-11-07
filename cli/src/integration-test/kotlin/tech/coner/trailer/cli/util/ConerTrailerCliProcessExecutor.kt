package tech.coner.trailer.cli.util

import java.nio.file.Path
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Person
import tech.coner.trailer.Policy
import tech.coner.trailer.di.Format
import tech.coner.trailer.eventresults.EventResultsType


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
        return Runtime.getRuntime().exec(commandArray, environment)
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