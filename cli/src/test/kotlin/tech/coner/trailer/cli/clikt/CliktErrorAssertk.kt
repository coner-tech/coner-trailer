package tech.coner.trailer.cli.clikt

import assertk.Assert
import assertk.assertions.prop
import com.github.ajalt.clikt.core.PrintHelpMessage

fun Assert<PrintHelpMessage>.command() = prop(PrintHelpMessage::command)