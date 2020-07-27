package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import org.coner.trailer.cli.io.ConfigGateway
import org.coner.trailer.cli.io.DatabaseConfiguration
import java.io.File

class Config : CliktCommand() {

    override fun run() = Unit

    class ListDatabases : CliktCommand(
            help = "List database configurations"
    ) {

        private val config by requireObject<ConfigGateway>()
        override fun run() {
            config.listDatabases().forEach { echo(it.render()) }
        }
    }
    class Database : CliktCommand(
            help = "Get database configuration or set it if given arguments"
    ) {
        private val config by requireObject<ConfigGateway>()

        val name: String by argument()

        class Set : OptionGroup() {

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
        }


        val set: Set? by Set().cooccurring()

        override fun run() {
            val value = set?.let { newValue ->
                val dbConfig = DatabaseConfiguration(name, newValue.crispyFishDatabase, newValue.snoozleDatabase)
                config.configureDatabase(dbConfig)
                dbConfig
            } ?: config.listDatabases().singleOrNull { it.name == name }
            if (value != null) {
                echo(value.render())
            } else {
                echo("No database found with name: $name.")
            }
        }
    }
    class RemoveDatabase : CliktCommand(
            help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
    ) {
        private val config by requireObject<ConfigGateway>()

        val name: String by argument()

        override fun run() {
            config.removeDatabase(name)
        }
    }
}

private fun DatabaseConfiguration.render() = """
    $name
        Crispy Fish:        $crispyFishDatabase
        Snoozle:            $snoozleDatabase
""".trimIndent()