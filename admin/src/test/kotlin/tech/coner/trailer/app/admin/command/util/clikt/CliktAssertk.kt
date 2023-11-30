package tech.coner.trailer.app.admin.command.util.clikt

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.messageContains
import com.github.ajalt.clikt.core.BadParameterValue

fun Assert<Throwable>.isBadParameter() = isInstanceOf<BadParameterValue>()

fun Assert<BadParameterValue>.isInvalidUUID() = messageContains("Not a UUID")