package tech.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators
import tech.coner.trailer.seasonpoints.TestRankingSorts
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorAddCommandTest : DIAware {

    lateinit var command: SeasonPointsCalculatorAddCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
        bindInstance { mapper }
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val rankingSortService: RankingSortService by instance()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    @MockK lateinit var mapper: SeasonPointsCalculatorParameterMapper
    @MockK lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonPointsCalculatorAddCommand(di, global)
            .context { console = testConsole }
    }

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
        every { service.create(eq(create)) } answers { Unit }
        val rtktperpcn = "--results-type-key-to-event-points-calculator-named"
        val viewRendered = "view rendered ${create.name}"
        every { view.render(create) } returns viewRendered

        command.parse(arrayOf(
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
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}
