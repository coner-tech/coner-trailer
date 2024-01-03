package tech.coner.trailer.app.admin.command.event.run

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotZero
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
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
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
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

        val testResult = command.test(arrayOf("${eventContext.event.id}"))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(rendered)
            stderr().isEmpty()
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

        val testResult = command.test(arrayOf(
            "--count", "$count",
            "${eventContext.event.id}"
        ))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(rendered)
            stderr().isEmpty()
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
        arrangeDefaultErrorHandling()
        val fixture = arrangeValidEvent()
        val throwable = ConstraintViolationException(Strings.constraintsEventRunLatestCountMustBeGreaterThanZero)
        coEvery { presenter.commit() } answers { Result.failure(throwable) }

        val testResult = command.test(arrayOf(
            "--count", "$count",
            "${eventContext.event.id}"
        ))

        verifyDefaultErrorHandlingInvoked(testResult, throwable)
        assertThat(testResult).all {
            statusCode().isNotZero()
            stdout().isEmpty()
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