package tech.coner.trailer.presentation.presenter.run

import kotlinx.coroutines.CoroutineScope
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.EventId
import tech.coner.trailer.Policy
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.presentation.adapter.EventRunLatestModelAdapter
import tech.coner.trailer.presentation.model.EventRunLatestModel
import tech.coner.trailer.presentation.presenter.BaseItemPresenter
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.PresenterCoroutineScope
import tech.coner.trailer.presentation.state.EventRunLatestState
import java.time.LocalDate

class EventRunLatestPresenter(
    override val argument: Argument,
    coroutineScope: PresenterCoroutineScope,
    override val adapter: EventRunLatestModelAdapter,
    private val eventService: EventService,
    private val eventContextService: EventContextService
) : BaseItemPresenter<
        EventRunLatestPresenter.Argument,
        EventRunLatestState,
        EventRunLatestModelAdapter,
        EventRunLatestModel
        >(), CoroutineScope by coroutineScope {

    override val entityDefault = EventRunLatestState()

    override suspend fun performLoad(): Result<EventRunLatestState> = runSuspendCatching {
        EventRunLatestState(
            eventContext = eventService.findByKey(argument.eventId)
                .map { eventContextService.load(it).getOrThrow() }
                .getOrThrow(),
            count = itemModel.itemValue.count
        )
    }

    override fun processArgument() = Unit // no-op

    data class Argument(val eventId: EventId) : Presenter.Argument
}

typealias EventRunLatestPresenterFactory = (EventRunLatestPresenter.Argument) -> EventRunLatestPresenter