package tech.coner.trailer.di.render.testsupport.text.view

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.view.*

val mockkTextViewRendererModule = DI.Module("tech.coner.trailer.testsupport.render.text.view") {
    val format = Format.TEXT

    // Club
    bindSingleton<ClubViewRenderer>(format) { mockk() }

    // Event
    bindSingleton<EventViewRenderer>(format) { mockk() }

    // Person
    bindSingleton<PersonViewRenderer>(format) { instance<PersonCollectionViewRenderer>(format) }
    bindSingleton<PersonCollectionViewRenderer>(format) { mockk() }

    // Policy
    bindSingleton<PolicyViewRenderer>(format) { instance<PolicyCollectionViewRenderer>(format) }
    bindSingleton<PolicyCollectionViewRenderer>(format) { mockk() }
}