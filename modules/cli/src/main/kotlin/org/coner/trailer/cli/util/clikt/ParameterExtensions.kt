package org.coner.trailer.cli.util.clikt

import com.github.ajalt.clikt.parameters.arguments.ArgumentTransformContext
import com.github.ajalt.clikt.parameters.options.OptionCallTransformContext
import org.coner.snoozle.util.isUuidPattern
import java.util.*

fun OptionCallTransformContext.toUuid(input: String): UUID {
    if (!isUuidPattern.matcher(input).matches())
        fail("Not a UUID")
    return UUID.fromString(input)
}

fun ArgumentTransformContext.toUuid(input: String): UUID {
    if (!isUuidPattern.matcher(input).matches())
        fail("Not a UUID")
    return UUID.fromString(input)
}