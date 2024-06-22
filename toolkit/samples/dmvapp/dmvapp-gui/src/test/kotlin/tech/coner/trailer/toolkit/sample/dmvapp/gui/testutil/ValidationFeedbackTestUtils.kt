package tech.coner.trailer.toolkit.sample.dmvapp.gui.testutil

import androidx.compose.ui.test.*

fun SemanticsNodeInteractionCollection.assertValidationFeedbackSupportingTextIsEmpty() =
    assertCountEquals(1)
        .assertAll(hasText(""))

fun SemanticsNodeInteractionCollection.assertValidationFeedbackSupportingTextIsExactly(vararg text: String) =
    assertCountEquals(text.size)
        .apply {
            text.forEachIndexed { index, text ->
                get(index).assertTextEquals(text)
            }
        }