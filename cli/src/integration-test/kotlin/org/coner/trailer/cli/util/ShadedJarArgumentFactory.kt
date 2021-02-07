package org.coner.trailer.cli.util

import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

@ExperimentalPathApi
class ShadedJarArgumentFactory : ProcessArgumentFactory {

    override fun build(): Array<String> {
        fun fromSystemProperties(): Array<String>? = try {
            val shadedJar = System.getProperty("coner-trailer-cli.shaded-jar")
            shadedJar?.let { arrayOf("java", "-jar", it) }
        } catch (t: Throwable) {
            null
        }
        fun fromResolvedShadedJarPath(): Array<String> {
            val outputDirectory = Paths.get("target")
            val shadedJarPath = outputDirectory.listDirectoryEntries()
                .singleOrNull { it.name.endsWith("-shaded.jar") }
                ?: throw IllegalStateException("Shaded jar not found")
            return arrayOf("java", "-jar", "target/${shadedJarPath.name}")
        }
        return fromSystemProperties()
            ?: fromResolvedShadedJarPath()
    }
}