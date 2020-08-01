package org.coner.trailer.cli.di

import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

fun serviceModule(databaseConfiguration: DatabaseConfiguration) = DI.Module("service") {
    bind<DatabaseConfiguration>() with instance(databaseConfiguration)
}