package tech.coner.trailer.cli.util

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun Process.awaitOutcome(): ProcessOutcome = runBlocking {
    val outputJob = async {
        val outputBuilder = StringBuilder()
        inputStream.bufferedReader().use { reader ->
            reader.lines().forEachOrdered { outputBuilder.appendLine(it) }
        }
        outputBuilder.toString().ifEmpty { null }
    }
    val errorJob = async {
        val errorBuilder = StringBuilder()
        errorStream.bufferedReader().use { reader ->
            reader.lines().forEachOrdered { errorBuilder.append(it) }
        }
        errorBuilder.toString().ifEmpty { null }
    }
    val exitCode: Deferred<Int> = async {
        var exitValue: Int? = null
        while (exitValue == null) {
            try {
                exitValue = exitValue()
            } catch (t: Throwable) {
                delay(1)
            }
        }
        exitValue
    }
    ProcessOutcome(
        output = outputJob.await(),
        error = errorJob.await(),
        exitCode = exitCode.await()
    )
}

data class ProcessOutcome(
    val output: String?,
    val error: String?,
    val exitCode: Int
)

fun Assert<ProcessOutcome>.output() = prop("output") { it.output }
fun Assert<ProcessOutcome>.error() = prop("error") { it.error }
fun Assert<ProcessOutcome>.exitCode() = prop("exitCode") { it.exitCode }
fun Assert<ProcessOutcome>.isSuccess() = exitCode().isEqualTo(0)

