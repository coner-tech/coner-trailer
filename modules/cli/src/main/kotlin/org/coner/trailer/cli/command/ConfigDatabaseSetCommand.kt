package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.DatabaseConfigurationView
import java.io.File

class ConfigDatabaseSetCommand(
        useConsole: CliktConsole,
        private val view: DatabaseConfigurationView,
        private val config: ConfigurationService
) : CliktCommand(
        name = "set",
        help = "Set database configuration"
) {

    init {
        context {
            console = useConsole
        }
    }

    val name: String by option().required()
    val crispyFishDatabase: File by option()
            .file(
                    mustExist = true,
                    canBeFile = false,
                    canBeDir = true,
                    mustBeReadable = true
            )
            .required()
    val snoozleDatabase: File by option()
            .file(
                    mustExist = true,
                    canBeFile = false,
                    canBeDir = true,
                    mustBeReadable = true,
                    mustBeWritable = true
            )
            .required()
    val default: Boolean by option().flag()

    override fun run() {
        val dbConfig = DatabaseConfiguration(
                name = name,
                crispyFishDatabase = crispyFishDatabase,
                snoozleDatabase = snoozleDatabase,
                default = default
        )
        config.configureDatabase(dbConfig)
        echo(view.render(dbConfig))
    }
}