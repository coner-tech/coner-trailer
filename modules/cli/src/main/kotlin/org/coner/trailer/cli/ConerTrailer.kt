package org.coner.trailer.cli

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.di.trailerCliGlobalDi
import org.kodein.di.instance

val app: CliktCommand by trailerCliGlobalDi.instance()

fun main(args: Array<out String>) {
    app.main(args)
}