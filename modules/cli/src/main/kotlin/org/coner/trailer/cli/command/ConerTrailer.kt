package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
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