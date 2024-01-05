package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import assertk.assertions.isNotZero
import assertk.assertions.isZero
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
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenterFactory

class PersonDeleteCommandTest : BaseDataSessionCommandTest<PersonDeleteCommand>() {

    override fun DirectDI.createCommand() = instance<PersonDeleteCommand>()

    private val validPresenterArgument = PersonDetailPresenter.Argument.GetById(TestPeople.ANASTASIA_RIGLER.id)

    private lateinit var presenterFactory: PersonDetailPresenterFactory

    override fun postSetup() {
        val directDi = direct
        presenterFactory = directDi.factory()
    }

    @Test
    fun `It should delete a person`() = runTest {
        val model: PersonDetailModel = mockk()
        val presenter = presenterFactory(validPresenterArgument)
        val mutex = Mutex(locked = true)
        coEvery { presenter.awaitLoadedItemModel() } coAnswers { mutex.withLock { Result.success(model) } }
        coEvery { presenter.load() } coAnswers { mutex.unlock() }
        coJustRun { presenter.delete() }

        val testResult = command.test(arrayOf(
            validPresenterArgument.id.toString()
        ))

        coVerifyAll {
            presenter.awaitLoadedItemModel()
            presenter.load()
            presenter.delete()
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEmpty()
        }
        confirmVerified(presenter)
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
        confirmVerified(presenter)
        verifyDefaultErrorHandlingInvoked(testResult, exception)
    }
}