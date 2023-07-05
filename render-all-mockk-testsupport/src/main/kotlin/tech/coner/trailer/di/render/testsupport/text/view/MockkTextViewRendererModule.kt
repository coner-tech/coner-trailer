package tech.coner.trailer.di.render.testsupport.text.view

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.view.ClubViewRenderer
import tech.coner.trailer.render.view.EventCollectionViewRenderer
import tech.coner.trailer.render.view.EventViewRenderer
import tech.coner.trailer.render.view.PersonCollectionViewRenderer
import tech.coner.trailer.render.view.PersonViewRenderer
import tech.coner.trailer.render.view.PolicyCollectionViewRenderer
import tech.coner.trailer.render.view.PolicyViewRenderer

val mockkTextViewRendererModule = DI.Module("tech.coner.trailer.testsupport.render.text.view") {
    val format = Format.TEXT

    // Club
    bindSingleton<ClubViewRenderer>(format) { mockk() }

    // Event
    bindSingleton<EventViewRenderer>(format) { mockk() }
    bindSingleton<EventCollectionViewRenderer>(format) { mockk() }

    // Person
    bindSingleton<PersonViewRenderer>(format) { instance<PersonCollectionViewRenderer>(format) }
    bindSingleton<PersonCollectionViewRenderer>(format) { mockk() }

    // Policy
    bindSingleton<PolicyViewRenderer>(format) { instance<PolicyCollectionViewRenderer>(format) }
    bindSingleton<PolicyCollectionViewRenderer>(format) { mockk() }
}