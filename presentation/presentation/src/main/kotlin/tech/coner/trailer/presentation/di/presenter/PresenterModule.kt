package tech.coner.trailer.presentation.di.presenter

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.di.constraint.presentationConstraintModule
import tech.coner.trailer.presentation.presenter.club.ClubDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenter
import tech.coner.trailer.toolkit.presentation.presenter.PresenterCoroutineScope

val presenterModule = DI.Module("tech.coner.trailer.presentation.presenter") {
    importOnce(presentationConstraintModule)

    // Club
    bind {
        scoped(DataSessionScope).singleton {
            ClubDetailPresenter()
        }
    }

    // Event
    bind {
        scoped(DataSessionScope).multiton { arg: EventRunLatestPresenter.Argument ->
            EventRunLatestPresenter(arg, instance(), instance(), instance(), instance())
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