package tech.coner.trailer.toolkit.sample.dmvapp.gui.page

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag

class DriversLicenseApplicationFormPage(
    private val test: ComposeUiTest
) {
    val nameField get() = test.onNodeWithTag("nameField", useUnmergedTree = true)
    val name get() = test.onNodeWithTag("name", useUnmergedTree = true)
    val nameFeedback get() = test.onAllNodesWithTag("nameValidationFeedback", useUnmergedTree = true)
    val ageField get() = test.onNodeWithTag("ageField", useUnmergedTree = true)
    val age get() = test.onNodeWithTag("age", useUnmergedTree = true)
    val ageFeedback get() = test.onAllNodesWithTag("ageValidationFeedback", useUnmergedTree = true)
    val licenseTypeDropdownMenuBox get() = test.onNodeWithTag("licenseTypeDropdownMenuBox", useUnmergedTree = true)
    val licenseTypeField = test.onNodeWithTag("licenseTypeField", useUnmergedTree = true)
    val licenseType get() = test.onNodeWithTag("licenseType", useUnmergedTree = true)
    val licenseTypeDropdownMenu get() = test.onNodeWithTag("licenseTypeDropdownMenu", useUnmergedTree = true)
    val licenseTypeDropdownMenuItems get() = test.onAllNodesWithTag("licenseTypeDropdownMenuItem", useUnmergedTree = true)
    val licenseTypeDropdownMenuItemTexts get() = test.onAllNodesWithTag("licenseTypeDropdownMenuItemText", useUnmergedTree = true)
    val licenseTypeFeedback get() = test.onAllNodesWithTag("licenseTypeValidationFeedback", useUnmergedTree = true)
    val resetButton get() = test.onNodeWithTag("resetButton", useUnmergedTree = true)
    val resetButtonIcon get() = test.onNodeWithTag("resetButtonIcon", useUnmergedTree = true)
    val applyButton get() = test.onNodeWithTag("applyButton", useUnmergedTree = true)
    val applyButtonText get() = test.onNodeWithTag("applyButtonText", useUnmergedTree = true)
    val applyButtonProgress get() = test.onNodeWithTag("applyButtonProgress", useUnmergedTree = true)
}

fun ComposeUiTest.DriversLicenseApplicationFormPage() = DriversLicenseApplicationFormPage(this)