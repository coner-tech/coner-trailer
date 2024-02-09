package tech.coner.trailer.presentation.di.presenter

import org.kodein.di.*
import tech.coner.trailer.Club
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.di.constraint.presentationConstraintModule
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.presenter.Presenter
import tech.coner.trailer.presentation.library.presenter.PresenterCoroutineScope
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.club.SecondDraftClubPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenter
import tech.coner.trailer.presentation.state.ClubPresenterState

val presenterModule = DI.Module("tech.coner.trailer.presentation.presenter") {
    importOnce(presentationConstraintModule)

    // Club
    bind {
        scoped(DataSessionScope).multiton { arg: Presenter.Argument.Nothing ->
            ClubPresenter(arg, instance(), instance(), instance())
        }
    }
    bind {
        scoped(DataSessionScope).multiton { initialState: ClubPresenterState ->
            SecondDraftClubPresenter(
                initialState = initialState,
                adapter = tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter(instance())
            )
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