package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path

class ConfigDatabaseAddCommand(
    di: DI,
    useConsole: CliktConsole,
) : CliktCommand(
        name = "add",
        help = "Add database configuration"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val view: DatabaseConfigurationView by instance()
    private val service: ConfigurationService by instance()

    private val name: String by option().required()
    private val crispyFishDatabase: Path by option()
            .path(
                    mustExist = true,
                    canBeFile = false,
                    canBeDir = true,
                    mustBeReadable = true
            )
            .required()
    private val snoozleDatabase: Path by option()
            .path(
                    mustExist = true,
                    canBeFile = false,
                    canBeDir = true,
                    mustBeReadable = true,
                    mustBeWritable = true
            )
            .required()
    class MotorsportRegOptions : OptionGroup() {
        val username: String? by option("--motorsportreg-username")
        val organizationId: String? by option("--motorsportreg-organization-id")
    }
    private val motorsportReg: MotorsportRegOptions by MotorsportRegOptions()
    private val default: Boolean by option().flag()

    override fun run() {
        val dbConfig = DatabaseConfiguration(
                name = name,
                crispyFishDatabase = crispyFishDatabase,
                snoozleDatabase = snoozleDatabase,
                motorsportReg = DatabaseConfiguration.MotorsportReg(
                        username = motorsportReg.username,
                        organizationId = motorsportReg.organizationId
                ),
                default = default
        )
        service.configureDatabase(dbConfig)
        echo(view.render(dbConfig))
    }
}