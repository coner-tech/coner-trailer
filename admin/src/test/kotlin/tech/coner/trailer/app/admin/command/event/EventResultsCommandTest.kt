package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.CliktCommandTestResult
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.presentation.di.Format
import tech.coner.trailer.presentation.model.eventresults.*
import java.util.stream.Stream

class EventResultsCommandTest : BaseDataSessionCommandTest<EventResultsCommand>() {

    override fun DirectDI.createCommand() = instance<EventResultsCommand>()

    private val eventContext: EventContext = TestEventContexts.Lscc2019Simplified.points1
    private lateinit var rawCalculator: RawEventResultsCalculator
    private val rawResults: OverallEventResults = TestOverallRawEventResults.Lscc2019Simplified.points1
    private val rawResultsModel: OverallEventResultsModel = mockk()
    private lateinit var paxCalculator: PaxEventResultsCalculator
    private val paxResults: OverallEventResults = TestOverallPaxEventResults.Lscc2019Simplified.points1
    private val paxResultsModel: OverallEventResultsModel = mockk()
    private lateinit var classCalculator: ClazzEventResultsCalculator
    private val clazzResults: ClassEventResults = TestClazzEventResults.Lscc2019Simplified.points1
    private val clazzResultsModel: ClassEventResultsModel = mockk()
    private lateinit var topTimesCalculator: TopTimesEventResultsCalculator
    private val topTimesResults: TopTimesEventResults = TestTopTimesEventResults.Lscc2019Simplified.points1
    private val topTimesResultsModel: TopTimesEventResultsModel = mockk()
    private lateinit var comprehensiveCalculator: ComprehensiveEventResultsCalculator
    private val comprehensiveResults: ComprehensiveEventResults = TestComprehensiveEventResults.Lscc2019Simplified.points1
    private val comprehensiveResultsModel: ComprehensiveEventResultsModel = mockk()
    private lateinit var individualCalculator: IndividualEventResultsCalculator
    private val individualResults: IndividualEventResults = TestIndividualEventResults.Lscc2019Simplified.points1
    private val individualResultsModel: IndividualEventResultsModel = mockk()

    @Test
    fun `It should print results as json to console`() {
        global.format = Format.JSON
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val event = eventContext.event
        val rawCalculator = command.calculatorFactories.raw(eventContext)
        coEvery { command.services.events.findByKey(event.id) } returns Result.success(event)
        coEvery { command.services.eventContexts.load(event) } returns Result.success(eventContext)
        val results = TestOverallRawEventResults.Lscc2019Simplified.points1
        every { rawCalculator.calculate() } returns results
        val render = "json"
        val adapter = command.adapters.overall
        val model: OverallEventResultsModel = mockk()
        every { adapter(any()) } returns model
        every { command.jsonView(model) } returns render

        val testResult = command.test(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(render)
        }
        coVerifySequence {
            command.services.events.findByKey(event.id)
            command.services.eventContexts.load(event)
            rawCalculator.calculate()
            command.adapters.overall(results)
            command.jsonView(model)
        }
        confirmVerified(
            command.services.events,
            command.services.eventContexts,
            rawCalculator,
            command.adapters.overall,
            command.jsonView
        )
    }

    private class EventResultTypeArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return StandardEventResultsTypes.all
                .map { Arguments { arrayOf(it) } }
                .stream()
        }
    }

    @ParameterizedTest
    @ArgumentsSource(EventResultTypeArgumentsProvider::class)
    fun `It should print results as plain text to console`(
        eventResultsType: EventResultsType,
    ) {
        arrangeCommon()
        arrangeTextViews()
        val global: GlobalModel = global
        global.format = Format.TEXT

        val testResult = command.test(arrayOf(
            "${eventContext.event.id}",
            "--type", eventResultsType.key,
        ))

        verifyCommon(eventResultsType)
        verifyTextViews(testResult, eventResultsType)
    }

    private fun arrangeCommon() {
        coEvery { command.services.events.findByKey(any()) } returns Result.success(eventContext.event)
        coEvery { command.services.eventContexts.load(any()) } returns Result.success(eventContext)
        rawCalculator = command.calculatorFactories.raw(eventContext).also { calculator -> every { calculator.calculate() } returns rawResults }
        paxCalculator = command.calculatorFactories.pax(eventContext).also { calculator -> every { calculator.calculate() } returns paxResults }
        classCalculator = command.calculatorFactories.clazz(eventContext).also { calculator -> every { calculator.calculate() } returns clazzResults }
        topTimesCalculator = command.calculatorFactories.topTimes(eventContext).also { calculator -> every { calculator.calculate() } returns topTimesResults }
        comprehensiveCalculator = command.calculatorFactories.comprehensive(eventContext).also { calculator -> every { calculator.calculate() } returns comprehensiveResults }
        individualCalculator = command.calculatorFactories.individual(eventContext).also { calculator -> every { calculator.calculate() } returns individualResults }
        every { command.adapters.overall(rawResults) } returns rawResultsModel
        every { command.adapters.overall(paxResults) } returns paxResultsModel
        every { command.adapters.clazz(any()) } returns clazzResultsModel
        every { command.adapters.topTimes(any()) } returns topTimesResultsModel
        every { command.adapters.comprehensive(any()) } returns comprehensiveResultsModel
        every { command.adapters.individual(any()) } returns individualResultsModel
    }

    private fun arrangeTextViews() {
        every { command.textViews.overall(model = any()) } returns "overall"
        every { command.textViews.clazz(model = any()) } returns "clazz"
        every { command.textViews.topTimes(model = any()) } returns "topTimes"
        every { command.textViews.comprehensive(model = any()) } returns "comprehensive"
        every { command.textViews.individual(model = any()) } returns "individual"
    }

    private fun verifyCommon(eventResultsType: EventResultsType) {
        coVerifySequence {
            command.services.events.findByKey(eventContext.event.id)
            command.services.eventContexts.load(eventContext.event)
            when (eventResultsType) {
                StandardEventResultsTypes.raw -> {
                    rawCalculator.calculate()
                    command.adapters.overall(rawResults)
                }
                StandardEventResultsTypes.pax -> {
                    paxCalculator.calculate()
                    command.adapters.overall(paxResults)
                }
                StandardEventResultsTypes.clazz -> {
                    classCalculator.calculate()
                    command.adapters.clazz(clazzResults)
                }
                StandardEventResultsTypes.topTimes -> {
                    topTimesCalculator.calculate()
                    command.adapters.topTimes(topTimesResults)
                }
                StandardEventResultsTypes.comprehensive -> {
                    comprehensiveCalculator.calculate()
                    command.adapters.comprehensive(comprehensiveResults)
                }
                StandardEventResultsTypes.individual -> {
                    individualCalculator.calculate()
                    command.adapters.individual(individualResults)
                }
                else -> throw IllegalArgumentException("unexpected eventResultsType: $eventResultsType")
            }
        }
        confirmVerified(command.services.events, command.services.eventContexts)
        confirmVerified(
            rawCalculator,
            paxCalculator,
            classCalculator,
            topTimesCalculator,
            comprehensiveCalculator,
            individualCalculator,
        )
        confirmVerified(
            command.adapters.overall,
            command.adapters.clazz,
            command.adapters.topTimes,
            command.adapters.comprehensive,
            command.adapters.individual
        )
    }

    fun verifyTextViews(testResult: CliktCommandTestResult, eventResultsType: EventResultsType) {
        val expectedOutput = when (eventResultsType) {
            StandardEventResultsTypes.raw -> {
                verify { command.textViews.overall(rawResultsModel) }
                "overall"
            }
            StandardEventResultsTypes.pax -> {
                verify { command.textViews.overall(paxResultsModel) }
                "overall"
            }
            StandardEventResultsTypes.clazz -> {
                verify { command.textViews.clazz(clazzResultsModel) }
                "clazz"
            }
            StandardEventResultsTypes.topTimes -> {
                verify { command.textViews.topTimes(topTimesResultsModel) }
                "topTimes"
            }
            StandardEventResultsTypes.comprehensive -> {
                verify { command.textViews.comprehensive(comprehensiveResultsModel) }
                "comprehensive"
            }
            StandardEventResultsTypes.individual -> {
                verify { command.textViews.individual(individualResultsModel) }
                "individual"
            }
            else -> {
                throw IllegalArgumentException("unexpected eventresultsType: $eventResultsType")
            }
        }
        confirmVerified(
            command.textViews.overall,
            command.textViews.clazz,
            command.textViews.topTimes,
            command.textViews.comprehensive,
            command.textViews.individual
        )
        assertThat(testResult).stdout().isEqualTo(expectedOutput)
    }
}