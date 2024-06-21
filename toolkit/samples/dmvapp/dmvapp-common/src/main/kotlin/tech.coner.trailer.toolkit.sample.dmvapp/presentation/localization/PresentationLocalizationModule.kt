package tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.new

val presentationLocalizationModule by DI.Module {
    bindSingleton<Localization> { new(::EnglishUsLocalization) }
}