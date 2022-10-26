package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import tech.coner.trailer.cli.util.clikt.handle
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.io.constraint.PortConstraints

class WebappConfigurationOptions(
    private val portConstraint: PortConstraints
) : OptionGroup() {

    private val port by port()
    private val exploratory by option(hidden = true).flag()

    fun mapToIo() = WebappConfiguration(
        port = port,
        exploratory = exploratory
    )

    fun port() = option()
        .int()
        .required()
        .validate { handle(portConstraint.invoke(it)) }
}
