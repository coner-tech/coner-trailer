package org.coner.trailer.cli.command

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

class ParticipantEventResultPointsCalculatorGetCommandTest {

    lateinit var command: ParticipantEventResultPointsCalculatorGetCommand

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
    fun `It should get calculator by id`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        every { service.findById(calculator.id) } returns calculator
        val viewRenders = "rendered ${calculator.name}"
        every { view.render(calculator) } returns viewRenders

        command.parse(arrayOf("--id", calculator.id.toString()))

        assertThat(console.output).isEqualTo(viewRenders)
        verifySequence {
            service.findById(calculator.id)
            view.render(calculator)
        }
    }

    @Test
    fun `It should get calculator by name`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        every { service.findByName(calculator.name) } returns calculator
        val viewRenders = "rendered ${calculator.id}"
        every { view.render(calculator) } returns viewRenders

        command.parse(arrayOf("--name", calculator.name))

        assertThat(console.output).isEqualTo(viewRenders)
        verifySequence {
            service.findByName(calculator.name)
            view.render(calculator)
        }
    }
}

private fun ParticipantEventResultPointsCalculatorGetCommandTest.arrangeCommand() {
    val di = DI {
        bind<ParticipantEventResultPointsCalculatorService>() with instance(service)
        bind<ParticipantEventResultPointsCalculatorView>() with instance(view)
    }
    command = ParticipantEventResultPointsCalculatorGetCommand(
            di = di,
            useConsole = console
    )
}