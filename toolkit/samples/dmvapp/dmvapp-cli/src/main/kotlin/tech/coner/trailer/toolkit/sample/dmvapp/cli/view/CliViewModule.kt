package tech.coner.trailer.toolkit.sample.dmvapp.cli.view

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.new

val cliViewModule by DI.Module {
    bindSingleton { new(::DriversLicenseView) }
}