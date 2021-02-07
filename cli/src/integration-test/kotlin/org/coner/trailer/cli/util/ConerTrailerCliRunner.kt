package org.coner.trailer.cli.util

import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

@ExperimentalPathApi
class ConerTrailerCliRunner(
    private val processArgumentFactory: ProcessArgumentFactory,
    private val configDir: Path,
    private val snoozleDir: Path,
    private val crispyFishDir: Path
) {

    private val logger: Logger = LoggerFactory.getLogger(ConerTrailerCliRunner::class.java)

    fun exec(vararg args: String, environment: Array<String> = emptyArray()): Process {
        val commandArray = processArgumentFactory.build()
            .toMutableList()
            .apply {
                addAll(arrayOf("--config-dir", "$configDir"))
                addAll(args)
            }
            .toTypedArray()
        logger.info { commandArray.joinToString(" ") }
        return Runtime.getRuntime().exec(commandArray, environment)
    }

    fun execConfigureDatabaseAdd(databaseName: String): Process {
        return exec(
            "config", "database", "add",
            "--name", databaseName,
            "--crispy-fish-database", "$crispyFishDir",
            "--snoozle-database", "$snoozleDir",
            "--motorsportreg-username", "motorsportreg-username",
            "--motorsportreg-organization-id", "motorsportreg-organization-id",
            "--default",
            environment = emptyArray()
        )
    }

}