package org.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestParticipantEventResultPointsCalculators
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.TestRankingSorts
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
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
        val create = TestSeasonPointsCalculatorConfigurations.lscc2019.copy()
        val groupingCalculator = TestParticipantEventResultPointsCalculators.lsccGroupingCalculator
        val overallCalculator = TestParticipantEventResultPointsCalculators.lsccOverallCalculator
        every { participantEventResultPointsCalculatorService.findByName(groupingCalculator.name) } returns groupingCalculator
        every { participantEventResultPointsCalculatorService.findByName(overallCalculator.name) } returns overallCalculator
        val rankingSort = TestRankingSorts.lscc
        every { rankingSortService.findByName(rankingSort.name) } returns create.rankingSort
        val rtktperpcn = "--results-type-key-to-participant-event-result-points-calculator-named"
        every { service.create(eq(create)) } answers { Unit }
        val viewRendered = "view rendered ${create.name}"
        every { view.render(create) } returns viewRendered

        command.parse(arrayOf(
                "--id", create.id.toString(),
                "--name", create.name,
                rtktperpcn, StandardResultsTypes.competitionGrouped.key, groupingCalculator.name,
                rtktperpcn, StandardResultsTypes.overallRawTime.key, overallCalculator.name,
                rtktperpcn, StandardResultsTypes.overallHandicapTime.key, overallCalculator.name,
                "--ranking-sort-named", rankingSort.name
        ))

        verifySequence {
            rankingSortService.findByName(rankingSort.name)
            participantEventResultPointsCalculatorService.findByName(groupingCalculator.name)
            participantEventResultPointsCalculatorService.findByName(overallCalculator.name)
            participantEventResultPointsCalculatorService.findByName(overallCalculator.name)
            service.create(eq(create))
            view.render(create)
        }
        assertThat(console.output).isEqualTo(viewRendered)
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