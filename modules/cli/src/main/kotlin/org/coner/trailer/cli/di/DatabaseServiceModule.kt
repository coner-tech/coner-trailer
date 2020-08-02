package org.coner.trailer.cli.di

import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorMapper
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun databaseServiceModule(databaseConfiguration: DatabaseConfiguration) = DI.Module("service") {
    bind<DatabaseConfiguration>() with instance(databaseConfiguration)
    bind<ConerTrailerDatabase>() with singleton { ConerTrailerDatabase(
            root = databaseConfiguration.snoozleDatabase.toPath())
    }
    bind<ParticipantEventResultPointsCalculatorService>() with singleton { ParticipantEventResultPointsCalculatorService(
            database = instance(),
            mapper = ParticipantEventResultPointsCalculatorMapper()
    ) }
}