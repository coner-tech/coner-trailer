package org.coner.trailer.cli.di

import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory
import org.coner.trailer.cli.io.ConfigurationService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val ioModule = DI.Module("io") {
    bind<ConfigurationService>() with singleton { ConfigurationService(
            appDirs = instance()
    ) }
    bind<AppDirs>() with singleton { AppDirsFactory.getInstance() }
}