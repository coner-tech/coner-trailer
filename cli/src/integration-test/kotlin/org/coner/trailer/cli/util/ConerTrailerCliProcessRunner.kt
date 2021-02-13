package org.coner.trailer.cli.util

import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.fixture.EventFixture
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class ConerTrailerCliProcessRunner(
    private val processCommandArrayFactory: ProcessCommandArrayFactory,
    private val appArgumentBuilder: IntegrationTestAppArgumentBuilder
) {

    private val logger: Logger = LoggerFactory.getLogger(ConerTrailerCliProcessRunner::class.java)

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

    fun execConfigureDatabaseAdd(databaseName: String): Process {
        return exec(
            *appArgumentBuilder.buildConfigureDatabaseAdd(databaseName)
        )
    }

    fun execEventAddCrispyFish(
        event: Event,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path
    ): Process {
        return exec(
            *appArgumentBuilder.buildEventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = crispyFishEventControlFile,
                crispyFishClassDefinitionFile = crispyFishClassDefinitionFile
            )
        )
    }

    fun execEventResultsOverall(
        event: Event,
        report: String,
        format: String? = null,
        output: String? = null,
    ): Process {
        return exec(
            *appArgumentBuilder.buildEventResultsOverall(
                event = event,
                report = report,
                format = format,
                output = output
            )
        )
    }

}