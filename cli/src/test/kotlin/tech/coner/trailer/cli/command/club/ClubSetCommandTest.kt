package tech.coner.trailer.cli.command.club

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.MissingOption
import io.mockk.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestClubs
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.ReadException
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenterFactory
import tech.coner.trailer.presentation.text.view.TextView

class ClubSetCommandTest : BaseDataSessionCommandTest<ClubSetCommand>() {

    override fun DirectDI.createCommand() = instance<ClubSetCommand>()

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
    fun `It should set club name`() = runTest {
        val mutex = Mutex(locked = true)
        val club = TestClubs.lscc
        val textViewRenders = "club"
        val model: ClubModel = mockk()
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { Result.success(model) } }
        justRun { model.name = any() }
        coEvery { presenter.commit() } returns Result.success(model)
        coJustRun { presenter.createOrUpdate() }
        every { textView(any()) } returns textViewRenders

        command.parse(arrayOf("--name", club.name))

        assertThat(testConsole).output().isEqualTo(textViewRenders)
        coVerify {
            presenter.awaitLoadedItemModel()
            presenter.load()
        }
        coVerifyOrder {
            model.name = club.name
            presenter.commit()
            presenter.createOrUpdate()
            textView(model)
        }
        confirmVerified(presenter, textView)
    }

    @Test
    fun `It should fail without club name argument`() = runTest {
        assertFailure { command.parse(emptyList()) }
            .isInstanceOf<MissingOption>()
            .messageContains("--name")
        confirmVerified(presenter, textView)
    }

    @Test
    fun `It should error if club fails to load`() = runTest {
        val club = TestClubs.lscc
        val exception = ReadException("corrupt club")
        val mutex = Mutex(locked = true)
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { Result.failure(exception) } }

        command.parse(arrayOf("--name", club.name))

        assertThat(testConsole).all {
            error().contains(exception.message)
            output().isEmpty()
        }
        coVerifyAll {
            presenter.load()
            presenter.awaitLoadedItemModel()
        }
        confirmVerified(presenter, textView)
    }
}