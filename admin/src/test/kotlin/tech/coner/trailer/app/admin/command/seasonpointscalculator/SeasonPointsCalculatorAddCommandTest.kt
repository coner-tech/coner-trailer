package tech.coner.trailer.app.admin.command.seasonpointscalculator

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators
import tech.coner.trailer.seasonpoints.TestRankingSorts
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

class SeasonPointsCalculatorAddCommandTest : BaseDataSessionCommandTest<SeasonPointsCalculatorAddCommand>() {

    private val rankingSortService: RankingSortService by instance()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val mapper: SeasonPointsCalculatorParameterMapper by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<SeasonPointsCalculatorAddCommand>()

    @Test
    fun `It should create a season points calculator`() {
        val create = TestSeasonPointsCalculatorConfigurations.lscc2019.copy()
        val groupingCalculator = TestEventPointsCalculators.lsccGroupedCalculator
        val overallCalculator = TestEventPointsCalculators.lsccOverallCalculator
        val rankingSort = TestRankingSorts.lscc
        every { rankingSortService.findByName(rankingSort.name) } returns create.rankingSort
        val resultsTypeToEventPointsCalculatorNamed = listOf(
                StandardEventResultsTypes.clazz.key to groupingCalculator.name,
                StandardEventResultsTypes.raw.key to overallCalculator.name,
                StandardEventResultsTypes.pax.key to overallCalculator.name
        )
        every { mapper.fromParameter(resultsTypeToEventPointsCalculatorNamed) } returns create.eventResultsTypeToEventPointsCalculator
        justRun { service.create(create) }
        val rtktperpcn = "--results-type-key-to-event-points-calculator-named"
        val viewRendered = "view rendered ${create.name}"
        every { view.render(create) } returns viewRendered

        val testResult = command.test(arrayOf(
                "--id", create.id.toString(),
                "--name", create.name,
                rtktperpcn, StandardEventResultsTypes.clazz.key, groupingCalculator.name,
                rtktperpcn, StandardEventResultsTypes.raw.key, overallCalculator.name,
                rtktperpcn, StandardEventResultsTypes.pax.key, overallCalculator.name,
                "--ranking-sort-named", rankingSort.name
        ))

        verifySequence {
            rankingSortService.findByName(rankingSort.name)
            mapper.fromParameter(eq(resultsTypeToEventPointsCalculatorNamed))
            service.create(eq(create))
            view.render(create)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}
