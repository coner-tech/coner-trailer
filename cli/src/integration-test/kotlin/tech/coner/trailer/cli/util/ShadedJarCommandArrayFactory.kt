package tech.coner.trailer.cli.util

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class ShadedJarCommandArrayFactory : ProcessCommandArrayFactory {

    private val pathToJava: Path
        get() = Path(System.getProperty("java.home"), "bin", "java")

    override fun build(): Array<String> {
        return fromSystemProperty()
            ?: fromResolvedShadedJarPath()
    }

    private fun fromSystemProperty(): Array<String>? = try {
        val shadedJar = System.getProperty("coner-trailer-cli.shaded-jar")
        shadedJar?.let { arrayOf(pathToJava.absolutePathString(), "-jar", it) }
    } catch (t: Throwable) {
        null
    }

    private fun fromResolvedShadedJarPath(): Array<String> {
        val outputDirectory = Paths.get("target")
        val shadedJarPath = outputDirectory.listDirectoryEntries()
            .singleOrNull { it.name.endsWith("-shaded.jar") }
            ?: throw IllegalStateException("Shaded jar not found")
        return arrayOf(pathToJava.absolutePathString(), "-jar", "target/${shadedJarPath.name}")
    }
}