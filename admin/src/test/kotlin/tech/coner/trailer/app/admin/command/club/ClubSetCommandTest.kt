package tech.coner.trailer.app.admin.command.club

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotZero
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestClubs
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.library.presenter.Presenter
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
        coEvery { presenter.createOrUpdate() } returns Result.success(club)
        every { textView(any()) } returns textViewRenders

        val testResult = command.test(arrayOf("--name", club.name))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(textViewRenders)
        }
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
        val testResult = command.test(emptyList())

        assertThat(testResult).all {
            statusCode().isNotZero()
            stderr().contains("--name")
        }
        confirmVerified(presenter, textView)
    }
}