package org.coner.trailer.cli.command

import assertk.Assert
import assertk.assertions.prop

fun Assert<RootCommand.Payload>.databaseConfiguration() = prop("databaseConfiguration") { it.databaseConfiguration }
