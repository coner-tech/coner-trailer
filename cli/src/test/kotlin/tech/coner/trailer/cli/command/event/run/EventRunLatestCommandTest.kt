package tech.coner.trailer.cli.command.event.run

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.constraint.ConstraintViolationException
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.model.EventRunLatestModel
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenter
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenterFactory
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventRunLatestCommandTest : BaseDataSessionCommandTest<EventRunLatestCommand>() {

    private lateinit var presenterFactory: EventRunLatestPresenterFactory
    private lateinit var presenter: EventRunLatestPresenter
    private lateinit var textView: TextCollectionView<RunModel, RunCollectionModel>

    private val eventContext = TestEventContexts.Lscc2019Simplified.points1

    private val rendered = "rendered"

    override fun DirectDI.createCommand() = instance<EventRunLatestCommand>()

    override fun postSetup() {
        val directDi = direct
        presenterFactory = directDi.factory()
        presenter = presenterFactory(EventRunLatestPresenter.Argument(eventContext.event.id))
        textView = directDi.instance()
    }

    @Test
    fun `It should echo 5 latest runs by default`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val fixture = arrangeValidEvent()

        command.parse(arrayOf("${eventContext.event.id}"))

        assertThat(testConsole).all {
            output().isEqualTo(rendered)
            error().isEmpty()
        }
        coVerify {
            presenter.load()
            presenter.awaitLoadedItemModel()
            presenter.commit()
            fixture.model.latestRuns
            textView(fixture.latestRunsModel)
        }
        confirmVerified(presenter, fixture.model, textView)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 4, 8, 16, 32, 64])
    fun `It should echo specified latest runs`(count: Int) {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val fixture = arrangeValidEvent()

        command.parse(arrayOf(
            "--count", "$count",
            "${eventContext.event.id}"
        ))

        assertThat(testConsole).all {
            output().isEqualTo(rendered)
            error().isEmpty()
        }
        coVerify {
            presenter.load()
            presenter.awaitLoadedItemModel()
            fixture.model.count = count
            presenter.commit()
            fixture.model.latestRuns
            textView(fixture.latestRunsModel)
        }

        confirmVerified(presenter, fixture.model, textView)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, -2, -4, -8, -16, -32, -64])
    fun `It should fail to validate when specified count is less than 1`(count: Int) {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val fixture = arrangeValidEvent()
        coEvery { presenter.commit() } answers { Result.failure(ConstraintViolationException(Strings.constraintsEventRunLatestCountMustBeGreaterThanZero)) }

        assertFailure {
            command.parse(arrayOf(
                "--count", "$count",
                "${eventContext.event.id}"
            ))
        }
            .isInstanceOf(ConstraintViolationException::class)
            .all {
                hasMessage(Strings.constraintsEventRunLatestCountMustBeGreaterThanZero)
            }

        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
        coVerify {
            presenter.load()
            presenter.awaitLoadedItemModel()
            fixture.model.count = count
            presenter.commit()
        }
        confirmVerified(presenter, textView, fixture.model, fixture.latestRunsModel)
    }

    private fun arrangeValidEvent(): Fixture {
        val mutex = Mutex(locked = true)
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        val latestRunsModel: RunCollectionModel = mockk()
        val model: EventRunLatestModel = mockk {
            every { latestRuns } returns latestRunsModel
            justRun { count = any() }
        }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { Result.success(model) } }
        coEvery { presenter.commit() } coAnswers { Result.success(model) }
        every { textView(any<RunCollectionModel>()) } returns rendered
        return Fixture(
            model = model,
            latestRunsModel = latestRunsModel
        )
    }

    data class Fixture(
        val model: EventRunLatestModel,
        val latestRunsModel: RunCollectionModel
    )
}