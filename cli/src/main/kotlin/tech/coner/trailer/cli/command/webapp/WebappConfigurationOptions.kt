package tech.coner.trailer.cli.command.webapp

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import tech.coner.trailer.cli.util.clikt.handle
import tech.coner.trailer.io.constraint.PortConstraints

class WebappConfigurationOptions(
    override val di: DI
) : OptionGroup(),
    DIAware {

    private val portConstraints: PortConstraints by instance()

    private val port by port()
    private val exploratory by exploratory()

    fun exploratory() = option(hidden = true)
        .flag()

    fun port() = option()
        .int()
        .validate { handle(portConstraints.invoke(it)) }
}
