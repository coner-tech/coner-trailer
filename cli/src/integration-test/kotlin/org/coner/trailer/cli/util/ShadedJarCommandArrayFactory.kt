package org.coner.trailer.cli.util

import java.nio.file.Paths

import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class ShadedJarCommandArrayFactory : ProcessCommandArrayFactory {

    override fun build(): Array<String> {
        return fromSystemProperty()
            ?: fromResolvedShadedJarPath()
    }

    private fun fromSystemProperty(): Array<String>? = try {
        val shadedJar = System.getProperty("coner-trailer-cli.shaded-jar")
        shadedJar?.let { arrayOf("java", "-jar", it) }
    } catch (t: Throwable) {
        null
    }

    private fun fromResolvedShadedJarPath(): Array<String> {
        val outputDirectory = Paths.get("target")
        val shadedJarPath = outputDirectory.listDirectoryEntries()
            .singleOrNull { it.name.endsWith("-shaded.jar") }
            ?: throw IllegalStateException("Shaded jar not found")
        return arrayOf("java", "-jar", "target/${shadedJarPath.name}")
    }
}