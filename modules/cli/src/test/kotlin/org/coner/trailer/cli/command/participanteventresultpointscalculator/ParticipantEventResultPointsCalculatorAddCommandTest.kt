package org.coner.trailer.cli.command.participanteventresultpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.TestParticipantEventResultPointsCalculators
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class ParticipantEventResultPointsCalculatorAddCommandTest {

    lateinit var command: ParticipantEventResultPointsCalculatorAddCommand

    @MockK
    lateinit var service: ParticipantEventResultPointsCalculatorService
    @MockK
    lateinit var view: ParticipantEventResultPointsCalculatorView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        arrangeCommand()
    }

    @Test
    fun `It should create calculator`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        every { service.create(eq(calculator)) } answers { Unit }
        val viewRenders = "created ${calculator.id}"
        every { view.render(eq(calculator)) } returns viewRenders

        command.parse(arrayOf(
                "--id", calculator.id.toString(),
                "--name", calculator.name,
                "--position-to-points", "1", "9",
                "--position-to-points", "2", "6",
                "--position-to-points", "3", "4",
                "--position-to-points", "4", "3",
                "--position-to-points", "5", "2",
                "--default-points", "1"
        ))

        verifySequence {
            service.create(eq(calculator))
            view.render(eq(calculator))
        }
        assertThat(console.output).isEqualTo(viewRenders)
    }
}


private fun ParticipantEventResultPointsCalculatorAddCommandTest.arrangeCommand() {
    val di = DI {
        bind<ParticipantEventResultPointsCalculatorService>() with instance(service)
        bind<ParticipantEventResultPointsCalculatorView>() with instance(view)
    }
    command = ParticipantEventResultPointsCalculatorAddCommand(
            di = di,
            useConsole = console
    )
}