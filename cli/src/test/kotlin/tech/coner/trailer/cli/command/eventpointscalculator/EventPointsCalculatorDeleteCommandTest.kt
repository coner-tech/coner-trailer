package tech.coner.trailer.cli.command.eventpointscalculator

import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
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

        command.parse(arrayOf(calculator.id.toString()))

        verifySequence {
            service.findById(calculator.id)
            service.delete(calculator)
        }
    }

}
