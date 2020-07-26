package org.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.ConerTrailer
import org.coner.trailer.cli.command.Config

fun main(args: Array<out String>) {
    val app = ConerTrailer().subcommands(
            Config().subcommands(
                    Config.ListDatabases(),
                    Config.RemoveDatabase(),
                    Config.Database()
            )
    )
    app.main(args)
}