package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import tech.coner.trailer.io.WebappConfiguration

class WebappConfigurationOptions : OptionGroup() {
    val port by option().int().required()

    fun toIo() = WebappConfiguration(
        port = port
    )
}