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
            config.listDatabases()
                    .forEach {
                        echo("""
                        ${it.name}
                            Crispy Fish:    ${it.crispyFishDatabase}
                            Snoozle:        ${it.snoozleDatabase}
                    """.trimIndent())
                    }
        }
    }
    class Database : CliktCommand(
            help = "Get database configuration or set it if given arguments"
    ) {
        private val config by requireObject<ConfigGateway>()

        val name: String by argument()

        class Value : OptionGroup() {

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


        val value: Value? by Value().cooccurring()

        override fun run() {
            val value = this.value
            if (value != null) {
                val dbConfig = DatabaseConfiguration(name, value.crispyFishDatabase, value.snoozleDatabase)
                config.configureDatabase(dbConfig)
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
