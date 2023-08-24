package tech.coner.trailer.presentation.testsupport.di.presenter

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenter

val mockkPresenterModule = DI.Module("tech.coner.trailer.presentation.presenter - mockk") {

    // Club
    bind<ClubPresenter> { scoped(DataSessionScope).multiton { _: Presenter.Argument.Nothing ->  mockk() } }

    // Event
    bind<EventRunLatestPresenter> { scoped(DataSessionScope).multiton { _: EventRunLatestPresenter.Argument -> mockk() } }

    // Person
    bind<PersonDetailPresenter> { scoped(DataSessionScope).multiton { _: PersonDetailPresenter.Argument -> mockk() } }
}