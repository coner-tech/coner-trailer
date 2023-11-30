package tech.coner.trailer.app.admin.command.club

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.messageContains
import com.github.ajalt.clikt.core.MissingOption
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestClubs
import tech.coner.trailer.app.admin.clikt.output
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
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
        val club = TestClubs.lscc
        val textViewRenders = "club"
        val model: ClubModel = mockk()
        every { presenter.itemModel } returns model
        justRun { model.name = any() }
        coEvery { presenter.commit() } returns Result.success(model)
        coJustRun { presenter.createOrUpdate() }
        every { textView(any()) } returns textViewRenders

        command.parse(arrayOf("--name", club.name))

        assertThat(testConsole).output().isEqualTo(textViewRenders)
        coVerifyOrder {
            presenter.itemModel
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
}