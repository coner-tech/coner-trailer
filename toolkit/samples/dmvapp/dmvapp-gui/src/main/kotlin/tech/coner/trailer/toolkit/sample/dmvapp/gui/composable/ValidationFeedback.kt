package tech.coner.trailer.toolkit.sample.dmvapp.gui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback

@Composable
fun ValidationFeedbackSupportingText(
    feedback: List<DriversLicenseApplicationModelFeedback>,
    testTagPrefix: String
): @Composable (() -> Unit) {
    return when {
        feedback.isNotEmpty() -> {
            {
                ValidationFeedbackSupportingTextContent(
                    feedback = feedback
                        .map { strings[it] },
                    testTagPrefix = testTagPrefix
                )
            }
        }
        else -> {
            {
                ValidationFeedbackSupportingTextContent(listOf(""), testTagPrefix)
            }
        }
    }
}

@Composable
private fun ValidationFeedbackSupportingTextContent(
    feedback: List<String>,
    testTagPrefix: String
) {
    Column {
        feedback.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .testTag("${testTagPrefix}ValidationFeedback")
            )
        }
    }
}