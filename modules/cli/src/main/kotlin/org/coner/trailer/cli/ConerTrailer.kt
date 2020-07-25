package org.coner.trailer.cli

import com.github.ajalt.clikt.core.*
import org.coner.trailer.cli.command.ConerTrailer
import org.coner.trailer.cli.command.Config

fun main(args: Array<out String>) = ConerTrailer()
        .subcommands(
                Config()
                        .subcommands(
                                Config.ListDatabases(),
                                Config.RemoveDatabase(),
                                Config.Database()
                        )
        )
        .main(args)