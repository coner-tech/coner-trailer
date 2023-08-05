package tech.coner.trailer.presentation.di.presenter

import org.kodein.di.*
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.PresenterCoroutineScope
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter

val presenterModule = DI.Module("tech.coner.trailer.presentation.presenter") {

    // Club
    bind {
        scoped(DataSessionScope).multiton { arg: Presenter.Argument.Nothing ->
            ClubPresenter(arg, instance(), instance(), instance())
        }
    }

    // Person
    bind {
        scoped(DataSessionScope).multiton { arg: PersonDetailPresenter.Argument ->
            PersonDetailPresenter(arg, instance(), instance(), instance())
        }
    }

    // etc
    bind { scoped(DataSessionScope).singleton { PresenterCoroutineScope(context) } }
}