package org.coner.trailer.cli.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class EventPointsCalculatorSetCommandTest {

    lateinit var command: EventPointsCalculatorSetCommand

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
    fun `It should set all properties of calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        val set = calculator.copy(
                name = "set",
                didNotStartPoints = 0,
                didNotFinishPoints = 1,
                positionToPoints = mapOf(
                        1 to 5,
                        2 to 4,
                        3 to 3
                ),
                defaultPoints = 2
        )
        every { service.update(eq(set)) } answers { Unit }
        val viewRendered = "view rendered ${set.name}"
        every { view.render(eq(set)) } returns viewRendered

        command.parse(arrayOf(
                set.id.toString(),
                "--name", set.name,
                "--did-not-start-points", set.didNotStartPoints.toString(),
                "--did-not-finish-points", set.didNotFinishPoints.toString(),
                "--position-to-points", "1", set.positionToPoints[1].toString(),
                "--position-to-points", "2", set.positionToPoints[2].toString(),
                "--position-to-points", "3", set.positionToPoints[3].toString(),
                "--default-points", set.defaultPoints.toString()
        ))

        verifySequence {
            service.findById(set.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(console.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should only rename a calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        val set = calculator.copy(
                name = "set"
        )
        every { service.update(eq(set)) } answers { Unit }
        val viewRendered = "view rendered ${set.name}"
        every { view.render(eq(set)) } returns viewRendered

        command.parse(arrayOf(
                set.id.toString(),
                "--name", set.name
        ))

        verifySequence {
            service.findById(set.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(console.output).isEqualTo(viewRendered)
    }
}

private fun EventPointsCalculatorSetCommandTest.arrangeCommand() {
    val di = DI {
        bind<EventPointsCalculatorService>() with instance(service)
        bind<EventPointsCalculatorView>() with instance(view)
    }
    command = EventPointsCalculatorSetCommand(
            di = di,
            useConsole = console
    )
}