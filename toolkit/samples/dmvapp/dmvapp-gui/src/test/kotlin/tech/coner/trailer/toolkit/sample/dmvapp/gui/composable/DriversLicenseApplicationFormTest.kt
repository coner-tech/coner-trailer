package tech.coner.trailer.toolkit.sample.dmvapp.gui.composable

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.*
import org.junit.Before
import org.junit.Test
import org.kodein.di.compose.withDI
import org.kodein.di.direct
import org.kodein.di.instance
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType.FullLicense
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.page.DriversLicenseApplicationFormPage
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.DmvAppScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication.DriversLicenseApplicationFormScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.testutil.assertValidationFeedbackSupportingTextIsEmpty
import tech.coner.trailer.toolkit.sample.dmvapp.gui.testutil.assertValidationFeedbackSupportingTextIsExactly
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.*
import kotlin.random.Random

class DriversLicenseApplicationFormTest {
    
    lateinit var strings: Strings

    @Before
    fun setup() {
        with(di.direct) {
            strings = instance()
        }
    }

    @Test
    fun showsDriversLicenseFormInInitialState() = runDriversLicenseFormUiTest { page ->
        page.nameField
            .assertExists()
            .assertTextEquals(strings.driversLicenseNameField)
        page.name
            .assertExists()
            .assertTextEquals("")
            .assertIsEnabled()
        page.nameFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()

        page.ageField
            .assertExists()
            .assertTextEquals(strings.driversLicenseAgeField)
        page.age
            .assertExists()
            .assertTextEquals("")
        page.ageFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()

        page.licenseTypeField
            .assertExists()
            .assertTextEquals(strings.driversLicenseLicenseTypeField)
        page.licenseType
            .assertExists()
            .assertTextEquals("")
        page.licenseTypeDropdownMenu
            .assertDoesNotExist()
        page.licenseTypeDropdownMenuItems
            .assertCountEquals(0)
        page.licenseTypeDropdownMenuItemTexts
            .assertCountEquals(0)
        page.licenseTypeDropdownMenuBox
            .assertExists()
            .performClick()
        page.licenseTypeDropdownMenu
            .assertExists()
        page.licenseTypeDropdownMenuItems
            .assertCountEquals(3)
        page.licenseTypeDropdownMenuItemTexts
            .assertCountEquals(3)
            .apply {
                get(0).assertTextContains(strings[LicenseType.GraduatedLearnerPermit])
                get(1).assertTextContains(strings[LicenseType.LearnerPermit])
                get(2).assertTextContains(strings[FullLicense])
            }
        page.licenseTypeDropdownMenuBox
            .performKeyInput { pressKey(Key.Escape) }
        page.licenseTypeDropdownMenu
            .assertDoesNotExist()
        page.licenseTypeFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()

        page.resetButton
            .assertExists()
            .assertIsNotEnabled()
        page.resetButtonIcon
            .assertExists()
            .assertContentDescriptionEquals(strings.driversLicenseApplicationFormReset)

        page.applyButton
            .assertExists()
            .assertIsEnabled()
        page.applyButtonText
            .assertExists()
            .assertTextEquals(strings.driversLicenseApplicationFormApply)

        page.applyButtonProgress
            .assertDoesNotExist()
    }

    @Test
    fun acceptsValidInput() = runDriversLicenseFormUiTest { page ->
        page.name.performTextInput("name")
        page.nameFeedback.assertValidationFeedbackSupportingTextIsEmpty()

        page.age.performTextInput("18")
        page.ageFeedback.assertValidationFeedbackSupportingTextIsEmpty()

        val fullLicenseText = strings[FullLicense]
        page.licenseType
            .assertExists()
            .assertTextEquals("")
        page.licenseTypeDropdownMenuBox.performClick()
        page.licenseTypeDropdownMenuItemTexts.apply {
            assertCountEquals(3)
            filterToOne(hasText(fullLicenseText))
                .performClick()
        }
        page.licenseType.assertTextEquals(fullLicenseText)
        page.licenseTypeFeedback.assertValidationFeedbackSupportingTextIsEmpty()

        page.resetButton
            .assertExists()
            .assertIsEnabled()

        page.applyButton
            .assertExists()
            .assertIsEnabled()
    }

    @Test
    fun rejectsInvalidInput() = runDriversLicenseFormUiTest { page ->
        page.applyButton
            .performClick()

        page.nameFeedback
            .assertValidationFeedbackSupportingTextIsExactly(strings[NameIsRequired])
        page.ageFeedback
            .assertValidationFeedbackSupportingTextIsExactly(strings[AgeIsRequired])
        page.licenseTypeFeedback
            .assertValidationFeedbackSupportingTextIsExactly(strings[LicenseTypeIsRequired])

        page.applyButton
            .assertIsNotEnabled()
    }

    @Test
    fun nameRecoversFromInvalidInput() = runDriversLicenseFormUiTest { page ->
        val nameText = "name"
        page.applyButton
            .performClick()

        page.name
            .performTextInput(nameText)
        page.name
            .assertTextEquals(nameText)

        page.nameFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()
    }

    @Test
    fun ageRecoversFromInvalidInput() = runDriversLicenseFormUiTest { page ->
        val ageText = "18"
        page.applyButton
            .performClick()

        page.age
            .performTextInput(ageText)
        page.age
            .assertTextEquals(ageText)

        page.ageFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()
    }

    @Test
    fun licenseTypeRecoversFromInvalidInput() = runDriversLicenseFormUiTest { page ->
        val licenseType = FullLicense
        val licenseTypeText = strings[licenseType]
        page.applyButton
            .performClick()

        page.licenseTypeDropdownMenuBox
            .performClick()
        page.licenseTypeDropdownMenuItemTexts
            .filterToOne(hasText(licenseTypeText))
            .performClick()

        page.licenseTypeFeedback
            .assertValidationFeedbackSupportingTextIsEmpty()
    }

    private fun runDriversLicenseFormUiTest(
        block: ComposeUiTest.(DriversLicenseApplicationFormPage) -> Unit
    ) = runComposeUiTest {
        setContent {
            withDI(di) {
                DriversLicenseApplicationFormScreen(
                    screen = DmvAppScreen.DriversLicenseApplication(Random.nextInt()),
                    openNavigationDrawer = {}
                )
            }
        }

        block(this, DriversLicenseApplicationFormPage())
    }
}