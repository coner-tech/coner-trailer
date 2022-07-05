package tech.coner.trailer.cli.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton

val testCliktModule = DI.Module("tech.coner.trailer.cli.di") {
    bindSingleton { CliCoroutineScope }
}