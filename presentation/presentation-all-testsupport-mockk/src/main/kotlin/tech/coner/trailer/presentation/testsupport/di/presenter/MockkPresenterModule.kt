package tech.coner.trailer.presentation.testsupport.di.presenter

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter

val mockkPresenterModule = DI.Module("tech.coner.trailer.presentation.presenter - mockk") {

    // Club
    bind<ClubPresenter> { scoped(DataSessionScope).singleton { mockk() } }

    // Person
    bind { scoped(DataSessionScope).multiton { _: PersonDetailPresenter.Argument -> mockk<PersonDetailPresenter>() } }
}