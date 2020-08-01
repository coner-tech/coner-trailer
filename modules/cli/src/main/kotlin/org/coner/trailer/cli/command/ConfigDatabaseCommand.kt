package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import java.io.File

class ConfigDatabaseCommand : CliktCommand(
        name = "database",
        help = "Grouping of database configuration commands"
) {

    override fun run() = Unit

    interface Subcommand {
        fun databaseNotFound(name: String) = "No database found with name: $name."

        fun render(config: DatabaseConfiguration) = config.run { """
    ${config.name} ${if (default) "[Default]" else ""}
        Crispy Fish:        $crispyFishDatabase
        Snoozle:            $snoozleDatabase
""".trimIndent() }
    }
}


class ConfigDatabaseListCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "list",
        help = "List database configurations"
), ConfigDatabaseCommand.Subcommand {

    override fun run() {
        config.listDatabases().forEach { echo(render(it)) }
    }
}

class ConfigDatabaseSetCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "set",
        help = "Set database configuration"
), ConfigDatabaseCommand.Subcommand {

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
        echo(render(dbConfig))
    }
}

class ConfigDatabaseRemoveCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
) {

    val dbConfig: DatabaseConfiguration by option(names = *arrayOf("--name"))
            .choice(config.listDatabasesByName())
            .required()

    override fun run() {
        if (dbConfig == config.noDatabase) throw Abort()
        config.removeDatabase(dbConfig)
    }
}

class ConfigDatabaseSetDefaultCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
), ConfigDatabaseCommand.Subcommand {

    val name: String by option().required()

    override fun run() {
        val value = config.listDatabases().singleOrNull { it.name == name }
        if (value != null) {
            val newValue = value.copy(
                    default = true
            )
            config.configureDatabase(newValue)
            echo(render(newValue))
        } else {
            echo(databaseNotFound(name))
        }
    }
}
