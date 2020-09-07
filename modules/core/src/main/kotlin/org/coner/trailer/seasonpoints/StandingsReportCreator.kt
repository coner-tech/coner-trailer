package org.coner.trailer.seasonpoints

import org.coner.trailer.*
import org.coner.trailer.eventresults.ComprehensiveResultsReport
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.ResultsType
import java.util.*
import kotlin.Comparator

class StandingsReportCreator {

    fun createComprehensiveStandingsReport(params: ComprehensiveStandingsReportParameters): StandingsReport {
        TODO()
    }

    class ComprehensiveStandingsReportParameters(
            val eventNumberToComprehensiveResultsReport: Map<Int, ComprehensiveResultsReport>
    )

    class CreateGroupedStandingsSectionsParameters(
            val resultsType: ResultsType,
            val season: Season,
            val eventToGroupedResultsReports: Map<SeasonEvent, GroupedResultsReport>,
            val takeTopEventScores: Int?,
            val rankingSort: RankingSort
    )

    fun createGroupedStandingsSections(param: CreateGroupedStandingsSectionsParameters): SortedMap<Grouping, StandingsReport.Section> {
        val eventToCalculator: Map<SeasonEvent, ParticipantEventResultPointsCalculator> = param.eventToGroupedResultsReports.keys.map { event: SeasonEvent ->
            val model = event.seasonPointsCalculatorConfigurationModel
                    ?: param.season.seasonPointsCalculatorConfigurationModel
            val calculator = model.resultsTypeToCalculatorMap[param.resultsType]
            checkNotNull(calculator) {
                "No season points calculator for results type: ${param.resultsType.title}"
            }
            event to calculator
        }.toMap()

        val groupingsToPersonStandingAccumulators: MutableMap<Grouping, MutableMap<Person, PersonStandingAccumulator>> = mutableMapOf()
        for ((event, groupedResultsReport) in param.eventToGroupedResultsReports) {
            val calculator = checkNotNull(eventToCalculator[event]) {
                "Failed to find season points calculator for event: ${event.event.name}"
            }
            for ((grouping, groupingResults) in groupedResultsReport.groupingsToResultsMap) {
                val accumulators: MutableMap<Person, PersonStandingAccumulator> = groupingsToPersonStandingAccumulators[grouping]
                        ?: mutableMapOf<Person, PersonStandingAccumulator>().apply {
                            groupingsToPersonStandingAccumulators[grouping] = this
                        }
                for (participantGroupingResult in groupingResults) {
                    if (participantGroupingResult.participant.person == null
                            || !participantGroupingResult.participant.seasonPointsEligible) {
                        continue
                    }
                    val accumulator = accumulators[participantGroupingResult.participant.person]
                            ?: PersonStandingAccumulator(
                                    person = participantGroupingResult.participant.person
                            ).apply {
                                accumulators[participantGroupingResult.participant.person] = this
                            }
                    with(accumulator) {
                        eventToPoints[event] = calculator.calculate(participantGroupingResult)
                        positionToFinishCount[participantGroupingResult.position] = (positionToFinishCount[participantGroupingResult.position] ?: 0).inc()
                    }
                }
            }
        }
        groupingsToPersonStandingAccumulators.forEach { (_, personToStandingAccumulators) ->
            personToStandingAccumulators.values.forEach { accumulator ->
                accumulator.score = accumulator.eventToPoints.values.sortedDescending()
                        .take(param.takeTopEventScores ?: Int.MAX_VALUE)
                        .sum()
            }
        }
        val groupingsToFinalAccumulators = groupingsToPersonStandingAccumulators.map { (grouping, peopleStandingAccumulators) ->
            val finalAccumulators = peopleStandingAccumulators.values
                    .toList()
                    .sortedWith(param.rankingSort.comparator)
            grouping to finalAccumulators
        }.toMap()
        groupingsToFinalAccumulators.forEach { (_, accumulators) ->
            accumulators.mapIndexed { index, accumulator ->
                accumulator.position = if (index > 0) {
                    val previousAccumulator = accumulators[index - 1]
                    val comparison = param.rankingSort.comparator.compare(accumulator, previousAccumulator)
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
        val sectionStandings: Map<Grouping, List<StandingsReport.Standing>> = groupingsToFinalAccumulators
                .map { (grouping, accumulators) ->
                    grouping to accumulators.map { accumulator ->
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
                .map { (grouping, standings) ->
                    grouping to StandingsReport.Section(
                            title = grouping.abbreviation,
                            standings = standings
                    )
                }
                .toMap()
                .toSortedMap()
    }

}