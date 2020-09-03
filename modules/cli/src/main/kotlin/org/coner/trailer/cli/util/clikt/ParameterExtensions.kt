package org.coner.trailer.cli.util.clikt

import com.github.ajalt.clikt.parameters.arguments.ArgumentTransformContext
import com.github.ajalt.clikt.parameters.options.OptionCallTransformContext
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