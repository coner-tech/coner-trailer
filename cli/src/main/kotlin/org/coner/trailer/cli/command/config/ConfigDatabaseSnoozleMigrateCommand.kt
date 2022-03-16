package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.groupChoice
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.cli.command.BaseCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import org.kodein.di.*

class ConfigDatabaseSnoozleMigrateCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "migrate",
    help = """
        Migrate the Coner Trailer Snoozle database version. Back up the database first.
        """.trimIndent()
),
    DIAware by di {

    override val diContext: DIContext<*> = diContext { global.requireEnvironment() }
    private val database: ConerTrailerDatabase by instance()

    sealed class MigrationStrategy(name: String? = null) : OptionGroup(name) {
        object Auto : MigrationStrategy()
        object Increment : MigrationStrategy()
        class Segment : MigrationStrategy("Segment migration strategy options") {
            val from: Int? by option().int()
            val to: Int by option().int().required()
        }
    }

    private val migrationStrategy: MigrationStrategy by option(
        help = """
            auto:       Migrate database version to highest available automatically.
            
            segment:    Migrate database version from an arbitrary segment to another. Referenced segment must be registered in the database.
            
            increment:  Migrate database version by one.
        """.trimIndent()
    )
        .groupChoice(
            "segment" to MigrationStrategy.Segment(),
            "increment" to MigrationStrategy.Increment,
            "auto" to MigrationStrategy.Auto
        )
        .required()

    override fun run() {
        val adminSession = database.openAdministrativeSession().getOrThrow()
        try {
            val result = when (val migrationStrategy = migrationStrategy) {
                is MigrationStrategy.Segment -> adminSession.migrateDatabase(
                    from = migrationStrategy.from,
                    to = migrationStrategy.to
                )
                MigrationStrategy.Increment -> adminSession.incrementalMigrateDatabase()
                MigrationStrategy.Auto -> adminSession.autoMigrateDatabase()
            }
            result.getOrThrow()
        } finally {
            adminSession.close()
        }
    }
}