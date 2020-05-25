package org.coner.trailer.seasonpoints

import org.coner.trailer.*
import org.coner.trailer.eventresults.ComprehensiveResultsReport
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.ResultsType

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
            val takeTopEventScores: Int
    )

    fun createGroupedStandingsSections(param: CreateGroupedStandingsSectionsParameters): Map<Grouping, StandingsReport.Section> {
        val groupingsToSectionTitles: Map<Grouping, String> = param.eventToGroupedResultsReports.values
                .flatMap { it.groupingsToResultsMap.keys }
                .distinct()
                .map { it to it.abbreviation }
                .toMap()
        val eventToCalculator: Map<SeasonEvent, ParticipantResultPositionMappedPointsCalculator> = param.eventToGroupedResultsReports.keys.map { event: SeasonEvent ->
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
                    val accumulator = accumulators[participantGroupingResult.participant.person]
                            ?: PersonStandingAccumulator(
                                    person = participantGroupingResult.participant.person
                            ).apply {
                                accumulators[participantGroupingResult.participant.person] = this
                            }
                    accumulator.eventToPoints[event] = calculator.calculate(participantGroupingResult)
                }
            }
        }
        groupingsToPersonStandingAccumulators.forEach { (_, personToStandingAccumulators) ->
            personToStandingAccumulators.values.forEach { accumulator ->
                accumulator.score = accumulator.eventToPoints.values.sortedDescending()
                        .take(param.takeTopEventScores)
                        .sum()
            }
        }
        val groupingsToFinalAccumulators = groupingsToPersonStandingAccumulators.map { (grouping, peopleStandingAccumulators) ->
            val finalAccumulators = peopleStandingAccumulators.values
                    .toList()
                    .sortedByDescending { it.score }
            grouping to finalAccumulators
        }.toMap()
        val sectionStandings: Map<Grouping, List<StandingsReport.Standing>> = groupingsToFinalAccumulators
                .map { (grouping, accumulators) ->
                    grouping to accumulators.mapIndexed { index, accumulator ->
                        StandingsReport.Standing(
                                position = index + 1,
                                person = accumulator.person,
                                eventToPoints = accumulator.eventToPoints.toMap(),
                                score = accumulator.score
                        )
                    }.toList()
                }.toMap()
        return sectionStandings.map { (grouping, standings) ->
            grouping to StandingsReport.Section(
                    title = grouping.abbreviation,
                    standings = standings
            )
        }.toMap()
    }

    private class PersonStandingAccumulator(
            val person: Person
    ) {
        val eventToPoints = mutableMapOf<SeasonEvent, Int>()
        var score: Int = 0
    }

}