package org.coner.trailer.cli.command.participanteventresultpointscalculator

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.TestParticipantEventResultPointsCalculators
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class ParticipantEventResultPointsCalculatorDeleteCommandTest {

    lateinit var command: ParticipantEventResultPointsCalculatorDeleteCommand

    @MockK
    lateinit var service: ParticipantEventResultPointsCalculatorService

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        arrangeCommand()
    }

    @Test
    fun `It should delete calculator`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        every { service.findById(calculator.id) } returns calculator
        every { service.delete(calculator) } answers { Unit }

        command.parse(arrayOf(calculator.id.toString()))

        verifySequence {
            service.findById(calculator.id)
            service.delete(calculator)
        }
    }

}

private fun ParticipantEventResultPointsCalculatorDeleteCommandTest.arrangeCommand() {
    val di = DI {
        bind<ParticipantEventResultPointsCalculatorService>() with instance(service)
    }
    command = ParticipantEventResultPointsCalculatorDeleteCommand(
            di = di,
            useConsole = console
    )
}