package org.coner.trailer.cli.command.seasonpointscalculator

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorAddCommandTest {

    lateinit var command: SeasonPointsCalculatorAddCommand

    @MockK
    lateinit var participantEventResultPointsCalculatorService: ParticipantEventResultPointsCalculatorService
    @MockK
    lateinit var rankingSortService: RankingSortService
    @MockK
    lateinit var service: SeasonPointsCalculatorConfigurationService
    @MockK
    lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        arrangeCommand()
    }

    @Test
    fun `It should create a season points calculator`() {
        TODO()
    }
}

private fun SeasonPointsCalculatorAddCommandTest.arrangeCommand() {
    val di = DI {
        bind<ParticipantEventResultPointsCalculatorService>() with instance(participantEventResultPointsCalculatorService)
        bind<RankingSortService>() with instance(rankingSortService)
        bind<SeasonPointsCalculatorConfigurationService>() with instance(service)
        bind<SeasonPointsCalculatorConfigurationView>() with instance(view)
    }
    command = SeasonPointsCalculatorAddCommand(
            di = di,
            useConsole = console
    )
}