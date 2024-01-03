package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestPeople
import tech.coner.trailer.app.admin.clikt.*
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenterFactory
import tech.coner.trailer.presentation.text.view.TextView

class PersonGetCommandTest : BaseDataSessionCommandTest<PersonGetCommand>() {

    override fun DirectDI.createCommand() = instance<PersonGetCommand>()

    private val validPresenterArgument = PersonDetailPresenter.Argument.GetById(TestPeople.ANASTASIA_RIGLER.id)

    private lateinit var presenterFactory: PersonDetailPresenterFactory
    private lateinit var textView: TextView<PersonDetailModel>

    override fun postSetup() {
        val directDi = direct
        presenterFactory = directDi.factory()
        textView = directDi.instance()
    }

    @Test
    fun `It should get a person`() = runTest {
        val model: PersonDetailModel = mockk()
        val presenter = presenterFactory(validPresenterArgument)
        val mutex = Mutex(locked = true)
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { Result.success(model) } }
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        val textViewRenders = "view rendered model"
        every { textView(model) } returns textViewRenders

        val testResult = command.test(arrayOf(
            validPresenterArgument.id.toString()
        ))

        coVerifyAll {
            presenter.awaitLoadedItemModel()
            presenter.load()
            textView(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(textViewRenders)
        }
        confirmVerified(presenter, textView)
    }

    @Test
    fun `It should error when id argument is missing`() {
        val testResult = command.test(emptyArray())

        assertThat(testResult).all {
            statusCode().isNotZero()
            stderr().contains("Error: missing argument <id>")
        }
    }

    @Test
    fun `It should error when id argument is not valid`() {
        val testResult = command.test(arrayOf("id argument position but not a valid uuid"))

        assertThat(testResult).all {
            statusCode().isNotZero()
            stderr().contains("Not a UUID")
        }
    }

    @Test
    fun `It should error when presenter fails to load`() = runTest {
        val presenter = presenterFactory(validPresenterArgument)
        val exception = Exception("deliberate failure")
        val mutex = Mutex(locked = true)
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { throw exception } }
        arrangeDefaultErrorHandling()

        val testResult = command.test(arrayOf(validPresenterArgument.id.toString()))

        coVerifyAll {
            presenter.load()
            presenter.awaitLoadedItemModel()
        }
        confirmVerified(presenter, textView)
        verifyDefaultErrorHandlingInvoked(testResult, exception)
    }
}