package tech.coner.trailer.app.admin.clikt

import assertk.Assert
import assertk.assertions.prop
import com.github.ajalt.clikt.core.PrintHelpMessage

fun Assert<PrintHelpMessage>.command() = prop(PrintHelpMessage::command)