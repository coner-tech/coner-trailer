package tech.coner.trailer.app.admin.command.eventpointscalculator

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

class EventPointsCalculatorDeleteCommandTest : BaseDataSessionCommandTest<EventPointsCalculatorDeleteCommand>() {

    private val service: EventPointsCalculatorService by instance()

    override fun DirectDI.createCommand() = instance<EventPointsCalculatorDeleteCommand>()

    @Test
    fun `It should delete calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        every { service.delete(calculator) } answers { Unit }

        val testResult = command.test(arrayOf(calculator.id.toString()))

        verifySequence {
            service.findById(calculator.id)
            service.delete(calculator)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEmpty()
        }
    }

}
