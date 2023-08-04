package tech.coner.trailer.presentation.text.view.eventresults

import tech.coner.trailer.presentation.model.eventresults.ComprehensiveEventResultsModel

class TextComprehensiveEventResultsView(
    private val lineSeparator: String,
    private val overallEventResultsView: TextOverallEventResultsView,
    private val classEventResultsView: MordantClassEventResultsView,
    private val topTimesEventResultsView: TextTopTimesEventResultsView
) : TextEventResultsView<ComprehensiveEventResultsModel>() {

    override fun StringBuilder.appendModel(model: ComprehensiveEventResultsModel) {
        val nameLineMatcher = Regex("${Regex.escape(model.eventName)}$lineSeparator")
        val dateLineMatcher = Regex("${Regex.escape(model.eventDate)}$lineSeparator")
        model.overallEventResults.forEach {
            appendLine(
                overallEventResultsView(it)
                    .replaceFirst(nameLineMatcher, "")
                    .replaceFirst(dateLineMatcher, "")
            )
        }
        appendLine(
            classEventResultsView(model.classEventResults)
                .replaceFirst(nameLineMatcher, "")
                .replaceFirst(dateLineMatcher, "")
        )
        appendLine(
            topTimesEventResultsView(model.topTimesEventResults)
                .replaceFirst(nameLineMatcher, "")
                .replaceFirst(dateLineMatcher, "")
        )
    }
}