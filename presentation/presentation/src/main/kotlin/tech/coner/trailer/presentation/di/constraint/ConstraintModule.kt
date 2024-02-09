package tech.coner.trailer.presentation.di.constraint

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.new
import tech.coner.trailer.presentation.constraint.EventRunLatestConstraints

val presentationConstraintModule = DI.Module("tech.coner.trailer.presentation.constraint") {
    // bindings for package: tech.coner.trailer.presentation.constraint

    // Event
    bindSingleton { new(::EventRunLatestConstraints) }
}