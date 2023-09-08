package tech.coner.trailer.cli.command.club

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.NotFoundException
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenterFactory
import tech.coner.trailer.presentation.text.view.TextView

class ClubGetCommandTest : BaseDataSessionCommandTest<ClubGetCommand>() {

    override fun DirectDI.createCommand() = instance<ClubGetCommand>()

    private lateinit var presenterFactory: ClubPresenterFactory
    private lateinit var presenter: ClubPresenter
    private lateinit var textView: TextView<ClubModel>

    override fun postSetup() {
        val directDi = direct
        presenterFactory = directDi.factory()
        presenter = presenterFactory(Presenter.Argument.Nothing)
        textView = directDi.instance()
    }

    @Test
    fun `It should get club`() = runTest {
        val loaded = Mutex(locked = true)
        val model: ClubModel = mockk()
        val textViewRenders = "club"
        coEvery { presenter.load() } coAnswers { loaded.unlock() }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { loaded.withLock { Result.success(model) } }
        every { textView(any()) } returns textViewRenders

        command.parse(emptyArray())

        assertThat(testConsole).output().isEqualTo(textViewRenders)
        coVerifyAll {
            presenter.load()
            presenter.awaitLoadedItemModel()
            textView(model)
        }
        confirmVerified(presenter, textView)
    }

    @Test
    fun `It should error if club not found`() {
        val loaded = Mutex(locked = true)
        val exception = NotFoundException("fail on purpose")
        coEvery { presenter.load() } coAnswers { loaded.unlock() }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { loaded.withLock { Result.failure(exception) } }
        arrangeDefaultErrorHandling()

        assertFailure {
            command.parse(emptyArray())
        }
            .isInstanceOf(ProgramResult::class)

        verifyDefaultErrorHandlingInvoked(exception)
        coVerifyAll {
            presenter.load()
            presenter.awaitLoadedItemModel()
        }
        confirmVerified(presenter, textView)
    }
}