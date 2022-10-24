package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.groups.groupChoice
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.int
import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.command.PermitNoDatabaseChosen
import tech.coner.trailer.cli.command.webapp.WebappConfigurationOptions
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappCommand(
    di: DI, global: GlobalModel
) : BaseCommand(
    di = di, global = global, name = "webapp", help = "Configure a webapp"
), PermitNoDatabaseChosen {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()

    private val webapp by option().enum<Webapp>().required()
    private val config by option().groupChoice(
        "set" to ConfigureWebappOptions.Set(), "unset" to ConfigureWebappOptions.Unset
    )
    private val unset by option().flag()

    sealed class ConfigureWebappOptions(name: String) : OptionGroup(name) {

        class Set : ConfigureWebappOptions("set") {
            val port by option().int().required()
            val exploratory by option(hidden = true).flag()
        }

        object Unset : ConfigureWebappOptions("unset")
    }

    override suspend fun coRun() {
        service.configureWebapp(
            webapp = webapp,
            webappConfig = when (val config = config) {
                is ConfigureWebappOptions.Set -> WebappConfiguration(
                    port = config.port, exploratory = config.exploratory
                )
                else -> null
            }
        )
    }
}