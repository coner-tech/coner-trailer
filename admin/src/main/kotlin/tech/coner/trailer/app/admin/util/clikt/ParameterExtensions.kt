package tech.coner.trailer.app.admin.util.clikt

import com.github.ajalt.clikt.parameters.arguments.ArgumentTransformContext
import com.github.ajalt.clikt.parameters.options.OptionCallTransformContext
import com.github.ajalt.clikt.parameters.options.OptionTransformContext
import com.github.ajalt.clikt.parameters.options.OptionValidator
import tech.coner.trailer.io.DatabaseConfiguration
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

fun OptionCallTransformContext.toUuid(input: String): UUID {
    try {
        return UUID.fromString(input)
    } catch (t: Throwable) {
        fail("Not a UUID")
    }
}

fun ArgumentTransformContext.toUuid(input: String): UUID {
    try {
        return UUID.fromString(input)
    } catch (t: Throwable) {
        fail("Not a UUID")
    }
}

fun OptionCallTransformContext.toLocalDate(input: String): LocalDate {
    try {
        return LocalDate.parse(input)
    } catch (dtpe: DateTimeParseException) {
        fail("Invalid date format")
    }
}

fun <T> OptionTransformContext.handle(result: Result<T>) {
    result.onFailure { fail(it.message ?: "Unknown failure") }
}

fun OptionCallTransformContext.toCrispyFishRelativePath(dbConfig: DatabaseConfiguration, input: Path): Path {
    return try {
        dbConfig.asRelativeToCrispyFishDatabase(input)
    } catch (t: Throwable) {
        fail("Unable to convert input to Crispy Fish relative path: ${t.message}")
    }
}
