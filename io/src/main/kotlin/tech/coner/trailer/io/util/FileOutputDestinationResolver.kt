package tech.coner.trailer.io.util

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.EventResultsFileNameGenerator
import tech.coner.trailer.eventresults.EventResultsType
import java.nio.file.Path
import kotlin.io.path.isDirectory

class FileOutputDestinationResolver(
    private val eventResultsFileNameGenerator: EventResultsFileNameGenerator
) {

    fun forEventResults(
        event: Event,
        type: EventResultsType,
        defaultExtension: String,
        path: Path?
    ): Path {
        return process(path) { resolve(eventResultsFileNameGenerator.build(
            event = event,
            type = type,
            extension = defaultExtension
        )) }
    }

    /**
     * Process a path:
     * - If path is null, invoke generator in the user's home directory
     * - If path is a directory, invoke generator on it
     * - Otherwise just return it
     * @param path optional directory or file name
     * @param generator generate the file name in the processed path
     * @return the path processed as described above
     */
    fun process(path: Path?, generator: Path.() -> Path): Path {
        return when {
            path == null -> org.apache.commons.lang3.SystemUtils.getUserHome().toPath().generator()
            path.isDirectory() -> path.generator()
            else -> path
        }
    }
}