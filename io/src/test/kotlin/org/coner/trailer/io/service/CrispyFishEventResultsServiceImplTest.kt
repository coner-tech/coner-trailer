package org.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.TestEvents
import org.coner.trailer.datasource.crispyfish.eventresults.CrispyFishOverallEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.GroupedEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallPaxEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallRawEventResultsFactory
import org.coner.trailer.eventresults.GroupEventResults
import org.coner.trailer.eventresults.IndividualEventResults
import org.coner.trailer.eventresults.IndividualEventResultsFactory
import org.coner.trailer.eventresults.OverallEventResults
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CrispyFishEventResultsServiceImplTest {

    lateinit var service: CrispyFishEventResultsServiceImpl

    @MockK lateinit var crispyFishClassService: CrispyFishClassService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var overallRawEventResultsFactory: (Policy) -> CrispyFishOverallEventResultsFactory
    @MockK lateinit var overallPaxEventResultsFactory: (Policy) -> CrispyFishOverallEventResultsFactory
    @MockK lateinit var mockRawEventResultsFactory: CrispyFishOverallEventResultsFactory
    @MockK lateinit var mockPaxEventResultsFactory: CrispyFishOverallEventResultsFactory
    @MockK lateinit var groupEventResultsFactory: (Policy) -> GroupedEventResultsFactory
    @MockK lateinit var mockGroupEventResultsFactory: GroupedEventResultsFactory
    @MockK lateinit var individualEventResultsFactory: IndividualEventResultsFactory

    @BeforeEach
    fun before() {
        service = CrispyFishEventResultsServiceImpl(
            crispyFishClassService = crispyFishClassService,
            crispyFishEventMappingContextService = crispyFishEventMappingContextService,
            overallRawEventResultsFactory = overallRawEventResultsFactory,
            overallPaxEventResultsFactory = overallPaxEventResultsFactory,
            groupEventResultsFactory = groupEventResultsFactory,
            individualEventResultsFactory = individualEventResultsFactory
        )
    }

    @Test
    fun `It should generate raw results`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val rawResults: OverallEventResults = mockk()
        arrangeBuildOverallTypeResults(
            factory = mockRawEventResultsFactory,
            toReturn = rawResults
        )

        val actual = service.buildRawResults(event)

        assertBuildOverallTypeResults(
            event = event,
            factory = overallRawEventResultsFactory,
            actual = actual,
            expected = rawResults
        )
    }

    @Test
    fun `It should generate pax results`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val paxResults: OverallEventResults = mockk()
        arrangeBuildOverallTypeResults(
            factory = mockPaxEventResultsFactory,
            toReturn = paxResults
        )

        val actual = service.buildPaxResults(event)

        assertBuildOverallTypeResults(
            event = event,
            factory = overallPaxEventResultsFactory,
            actual = actual,
            expected = paxResults
        )
    }

    @Test
    fun `It should generate class results`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val classResults: GroupEventResults = mockk()
        arrangeBuildGroupTypeResults(
            toReturn = classResults
        )

        val actual = service.buildClassResults(event)

        val eventCrispyFish = event.requireCrispyFish()
        verifySequence {
            groupEventResultsFactory(event.policy)
            crispyFishClassService.loadAllByAbbreviation(eventCrispyFish.classDefinitionFile)
            crispyFishEventMappingContextService.load(eventCrispyFish)
        }
        assertThat(actual).isSameAs(classResults)
    }

    @Test
    fun `It should generate individual results`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val rawResults: OverallEventResults = mockk()
        arrangeBuildOverallTypeResults(
            factory = mockRawEventResultsFactory,
            toReturn = rawResults
        )
        val paxResults: OverallEventResults = mockk()
        arrangeBuildOverallTypeResults(
            factory = mockPaxEventResultsFactory,
            toReturn = paxResults
        )
        val classResults: GroupEventResults = mockk()
        arrangeBuildGroupTypeResults(
            toReturn = classResults
        )
        val individualResults: IndividualEventResults = mockk()
        every { individualEventResultsFactory.factory(
            overallEventResults = any(),
            groupEventResults = any()
        ) } returns individualResults

        val actual = service.buildIndividualResults(event)

        verifySequence {
            overallRawEventResultsFactory(event.policy)
            mockRawEventResultsFactory.factory(any(), any(), any())
            overallPaxEventResultsFactory(event.policy)
            mockPaxEventResultsFactory.factory(any(), any(), any())
            groupEventResultsFactory(event.policy)
            mockGroupEventResultsFactory.factory(any(), any(), any())
        }
        assertThat(actual).isSameAs(individualResults)
    }
}

private fun CrispyFishEventResultsServiceImplTest.arrangeBuildOverallTypeResults(
    factory: CrispyFishOverallEventResultsFactory,
    toReturn: OverallEventResults
) {
    every { overallRawEventResultsFactory(any()) } returns mockRawEventResultsFactory
    every { overallPaxEventResultsFactory(any()) } returns mockPaxEventResultsFactory
    every { crispyFishClassService.loadAllByAbbreviation(
        crispyFishClassDefinitionFile = any()
    ) } returns mockk()
    every { crispyFishEventMappingContextService.load(any()) } returns mockk()
    every { factory.factory(
        eventCrispyFishMetadata = any(),
        allClassesByAbbreviation = any(),
        context = any()
    ) } returns toReturn
}

private fun CrispyFishEventResultsServiceImplTest.assertBuildOverallTypeResults(
    event: Event,
    factory: (Policy) -> CrispyFishOverallEventResultsFactory,
    actual: OverallEventResults,
    expected: OverallEventResults
) {
    val eventCrispyFish = event.requireCrispyFish()
    verifySequence {
        factory(event.policy)
        crispyFishClassService.loadAllByAbbreviation(eventCrispyFish.classDefinitionFile)
        crispyFishEventMappingContextService.load(eventCrispyFish)
    }
    assertThat(actual).isSameAs(expected)
}

private fun CrispyFishEventResultsServiceImplTest.arrangeBuildGroupTypeResults(
    toReturn: GroupEventResults
) {
    every { groupEventResultsFactory(any()) } returns mockGroupEventResultsFactory
    every { crispyFishClassService.loadAllByAbbreviation(
        crispyFishClassDefinitionFile = any()
    ) } returns mockk()
    every { crispyFishEventMappingContextService.load(any()) } returns mockk()
    every { mockGroupEventResultsFactory.factory(
        eventCrispyFishMetadata = any(),
        allClassesByAbbreviation = any(),
        context = any()
    ) } returns toReturn
}