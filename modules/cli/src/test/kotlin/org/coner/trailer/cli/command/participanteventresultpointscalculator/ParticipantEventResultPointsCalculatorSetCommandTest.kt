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

class ParticipantEventResultPointsCalculatorSetCommandTest {

    lateinit var command: ParticipantEventResultPointsCalculatorSetCommand

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
    fun `It should set all properties of calculator`() {
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
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
        val calculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
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

private fun ParticipantEventResultPointsCalculatorSetCommandTest.arrangeCommand() {
    val di = DI {
        bind<ParticipantEventResultPointsCalculatorService>() with instance(service)
        bind<ParticipantEventResultPointsCalculatorView>() with instance(view)
    }
    command = ParticipantEventResultPointsCalculatorSetCommand(
            di = di,
            useConsole = console
    )
}