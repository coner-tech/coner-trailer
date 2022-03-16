package org.coner.trailer.seasonpoints

import org.coner.trailer.Class
import org.coner.trailer.Person
import org.coner.trailer.Season
import org.coner.trailer.SeasonEvent
import org.coner.trailer.eventresults.ComprehensiveEventResults
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.GroupEventResults
import java.util.*

class StandingsReportCreator {

    fun createComprehensiveStandingsReport(params: ComprehensiveStandingsReportParameters): StandingsReport {
        TODO()
    }

    class ComprehensiveStandingsReportParameters(
            val eventNumberToComprehensiveEventResults: Map<Int, ComprehensiveEventResults>
    )

    class CreateGroupedStandingsSectionsParameters(
        val eventResultsType: EventResultsType,
        val season: Season,
        val eventToGroupEventResults: Map<SeasonEvent, GroupEventResults>,
        val configuration: SeasonPointsCalculatorConfiguration
    )

    fun createGroupedStandingsSections(
            param: CreateGroupedStandingsSectionsParameters
    ): SortedMap<Class, StandingsReport.Section> {
        val eventToCalculator: Map<SeasonEvent, EventPointsCalculator> = param.eventToGroupEventResults.keys.map { event: SeasonEvent ->
            val config = event.seasonPointsCalculatorConfiguration
                    ?: param.season.seasonPointsCalculatorConfiguration
            val eventPointsCalculator = config.eventResultsTypeToEventPointsCalculator[param.eventResultsType]
            checkNotNull(eventPointsCalculator) {
                "No event points calculator for results type: ${param.eventResultsType.title}"
            }
            event to eventPointsCalculator
        }.toMap()

        val groupsToPersonStandingAccumulators: MutableMap<Class, MutableMap<Person, PersonStandingAccumulator>> = mutableMapOf()
        for ((event, groupedEventResults) in param.eventToGroupEventResults) {
            val calculator = checkNotNull(eventToCalculator[event]) {
                "Failed to find season points calculator for event: ${event.event.name}"
            }
            for ((group, participantResults) in groupedEventResults.groupParticipantResults) {
                val accumulators: MutableMap<Person, PersonStandingAccumulator> = groupsToPersonStandingAccumulators[group]
                        ?: mutableMapOf<Person, PersonStandingAccumulator>().apply {
                            groupsToPersonStandingAccumulators[group] = this
                        }
                for (groupParticipantResult in participantResults) {
                    if (groupParticipantResult.participant.person == null
                            || !groupParticipantResult.participant.seasonPointsEligible) {
                        continue
                    }
                    val accumulator = accumulators[groupParticipantResult.participant.person]
                            ?: PersonStandingAccumulator(
                                    person = groupParticipantResult.participant.person
                            ).apply {
                                accumulators[groupParticipantResult.participant.person] = this
                            }
                    with(accumulator) {
                        eventToPoints[event] = calculator.calculate(groupParticipantResult)
                        positionToFinishCount[groupParticipantResult.position] = (positionToFinishCount[groupParticipantResult.position] ?: 0).inc()
                    }
                }
            }
        }
        groupsToPersonStandingAccumulators.forEach { (_, personToStandingAccumulators) ->
            personToStandingAccumulators.values.forEach { accumulator ->
                accumulator.score = accumulator.eventToPoints.values.sortedDescending()
                        .take(param.season.takeScoreCountForPoints ?: Int.MAX_VALUE)
                        .sum()
            }
        }
        val groupsToFinalAccumulators = groupsToPersonStandingAccumulators.map { (group, peopleStandingAccumulators) ->
            val finalAccumulators = peopleStandingAccumulators.values
                    .toList()
                    .sortedWith(param.configuration.rankingSort.comparator)
            group to finalAccumulators
        }.toMap()
        groupsToFinalAccumulators.forEach { (_, accumulators) ->
            accumulators.mapIndexed { index, accumulator ->
                accumulator.position = if (index > 0) {
                    val previousAccumulator = accumulators[index - 1]
                    val comparison = param.configuration.rankingSort.comparator.compare(accumulator, previousAccumulator)
                    if (comparison != 0) {
                        checkNotNull(previousAccumulator.position) + 1
                    } else {
                        accumulator.tie = true
                        previousAccumulator.tie = true
                        previousAccumulator.position
                    }
                } else {
                    1
                }
            }
        }
        val sectionStandings: Map<Class, List<StandingsReport.Standing>> = groupsToFinalAccumulators
                .map { (group, accumulators) ->
                    group to accumulators.map { accumulator ->
                        StandingsReport.Standing(
                                position = checkNotNull(accumulator.position),
                                tie = accumulator.tie,
                                person = accumulator.person,
                                eventToPoints = accumulator.eventToPoints.toSortedMap(),
                                score = accumulator.score
                        )
                    }.toList()
                }.toMap()
        return sectionStandings
                .mapNotNull { (group, standings) ->
                    group to StandingsReport.Section(
                            title = group.abbreviation,
                            standings = standings
                    )
                }
                .toMap()
                .toSortedMap()
    }

}