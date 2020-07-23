package org.coner.trailer.cli

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.sources.ExperimentalValueSourceApi
import com.github.ajalt.clikt.sources.PropertiesValueSource
import org.coner.trailer.cli.io.ConfigGateway

@OptIn(ExperimentalValueSourceApi::class)
class ConerTrailer : CliktCommand(
        name = "coner-trailer"
) {
    private val config by findOrSetObject { ConfigGateway() }

    override fun run() {
        context {
            valueSource = PropertiesValueSource.from(config.propertiesFile)
        }
        config.setup()
    }
}

class Config : CliktCommand() {

    private val config by requireObject<ConfigGateway>()

    override fun run() {
        echo(config.propertiesFile)
    }
}

fun main(args: Array<out String>) = ConerTrailer()
        .subcommands(
                Config()
        )
        .main(args)