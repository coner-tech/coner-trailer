package org.coner.trailer.cli.command.eventpointscalculator

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class EventPointsCalculatorDeleteCommandTest {

    lateinit var command: EventPointsCalculatorDeleteCommand

    @MockK
    lateinit var service: EventPointsCalculatorService

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        arrangeCommand()
    }

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

private fun EventPointsCalculatorDeleteCommandTest.arrangeCommand() {
    val di = DI {
        bind<EventPointsCalculatorService>() with instance(service)
    }
    command = EventPointsCalculatorDeleteCommand(
            di = di,
            useConsole = console
    )
}