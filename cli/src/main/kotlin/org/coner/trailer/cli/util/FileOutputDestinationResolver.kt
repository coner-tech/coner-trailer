package org.coner.trailer.cli.util

import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResultsReportFileNameGenerator
import org.coner.trailer.eventresults.ResultsType
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile

@ExperimentalPathApi
class FileOutputDestinationResolver(
    private val eventResultsReportFileNameGenerator: EventResultsReportFileNameGenerator
) {

    fun forEventResults(
        event: Event,
        type: ResultsType,
        defaultExtension: String,
        path: Path?
    ): Path {
        return process(path) { resolve(eventResultsReportFileNameGenerator.build(
            event = event,
            type = type,
            extension = defaultExtension
        )) }
    }

    /**
     * Process a path:
     * - If path is null, invoke generator in the current working directory
     * - If path is a directory, invoke generator on it
     * - Otherwise just return it
     * @param path optional directory or file name
     * @param generator generate the file name in the processed path
     * @return the path processed as described above
     */
    fun process(path: Path?, generator: Path.() -> Path): Path {
        return when {
            path == null -> Paths.get("").generator()
            path.isDirectory() -> path.generator()
            else -> path
        }
    }
}