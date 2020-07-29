package org.coner.trailer.cli

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.di.di
import org.kodein.di.instance

val app: CliktCommand by di.instance()

fun main(args: Array<out String>) {
    app.main(args)
}