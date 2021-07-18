package org.coner.trailer.cli.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class EventPointsCalculatorAddCommandTest {

    lateinit var command: EventPointsCalculatorAddCommand

    @MockK
    lateinit var constraints: EventPointsCalculatorPersistConstraints
    @MockK
    lateinit var service: EventPointsCalculatorService
    @MockK
    lateinit var view: EventPointsCalculatorView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        arrangeCommand()
    }

    @Test
    fun `It should create calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { constraints.hasUniqueName(calculator.id, calculator.name) } returns true
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


private fun EventPointsCalculatorAddCommandTest.arrangeCommand() {
    val di = DI {
        bind<EventPointsCalculatorPersistConstraints>() with instance(constraints)
        bind<EventPointsCalculatorService>() with instance(service)
        bind<EventPointsCalculatorView>() with instance(view)
    }
    command = EventPointsCalculatorAddCommand(
            di = di,
            useConsole = console
    )
}