package tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.new

val presentationAdapterModule by DI.Module {
    bindSingleton { new(::DriversLicenseApplicationModelAdapters) }
}