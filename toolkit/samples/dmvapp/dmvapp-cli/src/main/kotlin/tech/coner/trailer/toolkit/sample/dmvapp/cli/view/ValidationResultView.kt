package tech.coner.trailer.toolkit.sample.dmvapp.cli.view

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.table.Table
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import kotlin.reflect.KProperty1

class ValidationResultView<INPUT, FEEDBACK : Feedback<INPUT>> (
    private val terminal: Terminal,
    private val strings: Strings,
    private val fieldStringsMap: Map<KProperty1<INPUT, *>, String>,
    private val messageFn: (FEEDBACK) -> String,
) {
    operator fun invoke(
        validationOutcome: ValidationOutcome<INPUT, FEEDBACK>,
    ): Table {
        return table {
            body {
                validationOutcome.feedbackByProperty.forEach { (property, feedbacks) ->
                    feedbacks.forEachIndexed { index, feedback ->
                        row {
                            if (index == 0) {
                                cell(fieldStringsMap[property]) {
                                    columnSpan = feedbacks.size
                                }
                            }
                            cell(strings.validation[feedback.severity]) {
                                style(
                                    color = when (feedback.severity) {
                                        Severity.Error -> TextColors.red
                                        Severity.Warning -> TextColors.yellow
                                        Severity.Success -> TextColors.green
                                        Severity.Info -> TextColors.blue
                                    }
                                )
                            }
                            cell(messageFn(feedback))
                        }
                    }
                }
            }
        }
    }
}