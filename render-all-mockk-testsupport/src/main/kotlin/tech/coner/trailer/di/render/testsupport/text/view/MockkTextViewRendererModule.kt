package tech.coner.trailer.di.render.testsupport.text.view

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.view.ClubViewRenderer
import tech.coner.trailer.render.view.EventViewRenderer
import tech.coner.trailer.render.view.PersonCollectionViewRenderer
import tech.coner.trailer.render.view.PersonViewRenderer

val mockkTextViewRendererModule = DI.Module("tech.coner.trailer.testsupport.render.text.view") {
    val format = Format.TEXT

    // Club
    bindSingleton<ClubViewRenderer>(format) { mockk() }

    // Event
    bindSingleton<EventViewRenderer>(format) { mockk() }

    // Person
    bindSingleton<PersonCollectionViewRenderer>(format) { mockk() }
    bindSingleton<PersonViewRenderer>(format) { instance<PersonCollectionViewRenderer>(format) }
}