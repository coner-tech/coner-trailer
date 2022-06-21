package tech.coner.trailer.datasource.crispyfish.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.key
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestPeople
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.eventresults.GroupedEventResultsFactory
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.hasSameIdAs
import tech.coner.trailer.seasonpoints.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class StandingsReportCreatorFromCrispyFishTest {

    lateinit var creator: StandingsReportCreator

    @TempDir lateinit var fixtureRoot: Path

    @BeforeEach
    fun before() {
        creator = StandingsReportCreator()
    }

    @Test
    fun `It should produce season points standings for LSCC 2019 Simplified`() {
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        val competitionGroupedEventResults = seasonFixture.events.associate { eventFixture ->
            val allRegistrations = eventFixture.registrations()
            val staging = eventFixture.stagingRuns(allRegistrations)
            val context = CrispyFishEventMappingContext(
                allClassDefinitions = seasonFixture.classDefinitions,
                allRegistrations = allRegistrations,
                allRuns = eventFixture.allRuns(allRegistrations, staging),
                staging = staging,
                runCount = eventFixture.runCount
            )
            val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(
                runCount = context.runCount
            )
            val creator = GroupedEventResultsFactory(
                groupParticipantResultMapper = eventFixture.groupedParticipantResultMapper,
                rawTimeParticipantResultMapper = eventFixture.rawTimeParticipantResultMapper,
                scoredRunsComparatorProvider = { scoredRunsComparator }
            )
            val groupEventResults = creator.factory(
                eventCrispyFishMetadata = eventFixture.coreSeasonEvent.event.crispyFish!!,
                allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
                context = context
            )
            eventFixture.coreSeasonEvent to groupEventResults
        }
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
                eventResultsType = StandardEventResultsTypes.clazz,
                season = seasonFixture.season,
                eventToGroupEventResults = competitionGroupedEventResults,
                configuration = TestSeasonPointsCalculatorConfigurations.lscc2019Simplified
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            key(TestClasses.Lscc2019.NOV).all {
                standings().all {
                    hasSize(4)
                    index(0).all {
                        hasPosition(1)
                        isNotTie()
                        hasScore(18)
                        person().hasSameIdAs(TestPeople.BRANDY_HUFF)
                    }
                    index(1).all {
                        hasPosition(2)
                        isNotTie()
                        hasScore(12)
                        person().hasSameIdAs(TestPeople.BRYANT_MORAN)
                    }
                    index(2).all {
                        hasPosition(3)
                        isNotTie()
                        hasScore(10)
                        person().hasSameIdAs(TestPeople.DOMINIC_ROGERS)
                    }
                    index(3).all {
                        hasPosition(4)
                        isNotTie()
                        hasScore(7)
                        person().hasSameIdAs(TestPeople.BENNETT_PANTONE)
                    }
                }
            }
            key(TestClasses.Lscc2019.HS).all {
                standings().all {
                    hasSize(2)
                    index(0).all {
                        hasPosition(1)
                        isNotTie()
                        hasScore(18)
                        person().hasSameIdAs(TestPeople.ANASTASIA_RIGLER)
                    }
                    index(1).all {
                        hasPosition(2)
                        isNotTie()
                        hasScore(6)
                        person().hasSameIdAs(TestPeople.REBECCA_JACKSON)
                    }
                }
            }
            key(TestClasses.Lscc2019.STR).all {
                standings().all {
                    hasSize(3)
                    index(0).all {
                        hasPosition(1)
                        isNotTie()
                        hasScore(18)
                        person().hasSameIdAs(TestPeople.REBECCA_JACKSON)
                    }
                    index(1).all {
                        hasPosition(2)
                        isNotTie()
                        hasScore(13)
                        person().hasSameIdAs(TestPeople.EUGENE_DRAKE)
                    }
                    index(2).all {
                        hasPosition(3)
                        isNotTie()
                        hasScore(12)
                        person().hasSameIdAs(TestPeople.JIMMY_MCKENZIE)
                    }
                }
            }
        }
    }
}