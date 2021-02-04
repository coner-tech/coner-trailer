package org.coner.trailer.cli.util

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

@ExperimentalPathApi
class ConerTrailerCliRunner(
    private val configDir: Path,
    private val snoozleDir: Path,
    private val crispyFishDir: Path
) {

    private val baseCommand: Array<String> by lazy {
        fun fromSystemProperties(): Array<String>? {
            val baseProperty = "coner-trailer-cli.command"
            return try {
                listOf(
                    System.getProperty("${baseProperty}.0"),
                    System.getProperty("${baseProperty}.1"),
                    System.getProperty("${baseProperty}.2")
                )
                    .filter { it != "ignore" }
                    .toTypedArray()
            } catch (t: Throwable) {
                null
            }
        }
        fun fromResolvedShadedJarPath(): Array<String>? {
            val outputDirectory = Paths.get("target")
            val shadedJarPath = outputDirectory.listDirectoryEntries()
                .singleOrNull { it.name.endsWith("-shaded.jar") }
            return shadedJarPath?.let {
                arrayOf("java", "-jar", "target/${shadedJarPath.name}")
            }
        }
        fromSystemProperties()
            ?: fromResolvedShadedJarPath()
            ?: throw IllegalStateException("Unable to build baseCommand. Missing BOTH system properties AND shaded jar)")
    }

    fun exec(vararg args: String): Process {
        val command = buildCommand(*args)
        println("command: ${command.joinToString(", ")}")
        return Runtime.getRuntime().exec(command)
    }

    fun execConfigured(vararg args: String): Process {
        val execArgs = mutableListOf("--config-dir", "$configDir")
            .apply { addAll(args) }
            .toTypedArray()
        return exec(*execArgs)
    }

    fun execConfigureDatabaseAdd(testName: String): Process {
        return exec(
            "config", "database", "add",
            "--name", testName,
            "--crispy-fish-database", "$crispyFishDir",
            "--snoozle-database", "$snoozleDir",
            "--motorsportreg-username", "motorsportreg-username",
            "--motorsportreg-organization-id", "motorsportreg-organization-id",
            "--default"
        )
    }

    private fun buildCommand(vararg args: String): Array<String> {
        return baseCommand.toMutableList()
            .apply { addAll(args) }
            .toTypedArray()
    }
}