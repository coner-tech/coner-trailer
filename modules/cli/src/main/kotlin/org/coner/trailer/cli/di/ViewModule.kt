package org.coner.trailer.cli.di

import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider

val viewModule = DI.Module("view") {
    bind<DatabaseConfigurationView>() with provider { DatabaseConfigurationView() }
}