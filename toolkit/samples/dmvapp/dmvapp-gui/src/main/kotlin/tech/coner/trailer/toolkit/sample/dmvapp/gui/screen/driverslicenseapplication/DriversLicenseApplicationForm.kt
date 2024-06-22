
package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.gui.composable.ConerTopLevelTopAppBar
import tech.coner.trailer.toolkit.sample.dmvapp.gui.composable.ValidationFeedbackSupportingText
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.DmvAppScreen
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.*
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.FocusFirstFocusRequester.Companion.FocusFirstFocusRequester
import tech.coner.trailer.toolkit.sample.dmvapp.gui.window.DriversLicenseWindow
import tech.coner.trailer.toolkit.sample.dmvapp.gui.window.ExceptionWindow
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationRejectionModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.DriversLicenseApplicationPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.isInvalid

@Composable
fun DriversLicenseApplicationFormScreen(
    screen: DmvAppScreen.DriversLicenseApplication,
    openNavigationDrawer: () -> Unit
) {
    val presenter: DriversLicenseApplicationPresenter by rememberInstance()

    val coroutineScope = rememberCoroutineScope { Dispatchers.Main.immediate + CoroutineName("DriversLicenseApplicationFormScreen") }

    val focusFirstError = remember {
        FocusFirstFocusRequester(
            properties = listOf(
                DriversLicenseApplicationModel::name,
                DriversLicenseApplicationModel::age,
                DriversLicenseApplicationModel::licenseType
            )
        )
    }

    val name = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }

    val performReset: () -> Unit = {
        coroutineScope.launch {
            name.value = ""
            age.value = ""
            presenter.reset()
        }
    }

    presenter.stateFlow
        .map { it.outcome }
        .collectAsState(null).value
        ?.onSuccess { either ->
            either
                .onLeft { rejection ->
                    val rejectionMessage = when (rejection) {
                        is DriversLicenseApplicationRejectionModel.Sassed -> strings[rejection.sass]
                        is DriversLicenseApplicationRejectionModel.LegallyProhibited -> strings.driversLicenseApplicationRejectionLegallyProhibited
                        is DriversLicenseApplicationRejectionModel.Invalid -> null
                    }
                    if (rejectionMessage != null) {
                        DriversLicenseApplicationRejectionDialog(
                            onDismissRequest = {
                                coroutineScope.launch {
                                    presenter.clearOutcome()
                                }
                            },
                            rejectionMessage = rejectionMessage
                        )
                    }
                }
                .onRight { driversLicense ->
                    DriversLicenseWindow(
                        driversLicense = driversLicense,
                        onCloseRequest = performReset
                    )
                }
        }
        ?.onFailure {
            ExceptionWindow(
                cause = it,
                onCloseRequest = {
                    coroutineScope.launch {
                        presenter.clearOutcome()
                    }
                }
            )
        }



    var serviceSettingsDisplayed by remember { mutableStateOf(false) }
    if (serviceSettingsDisplayed) {
        DriversLicenseApplicationServiceSettingsDialog(
            onDismissRequest = { serviceSettingsDisplayed = false }
        )
    }

    DisposableEffect(screen) {
        with(coroutineScope) {
            presenter.validationOutcomeFlow
                .onEach { validationResult ->
                    validationResult.feedbackByProperty.entries
                        .firstOrNull { (_, feedbacks) ->
                            feedbacks.any {
                                it.severity == Severity.Error
                            }
                        }
                        ?.key
                        ?.also { property -> focusFirstError.requestFocus(property) }
                }
                .launchIn(this@with)
        }
        onDispose {
            runBlocking { presenter.reset() }
            coroutineScope.cancel()
        }
    }

    DriversLicenseApplicationFormContent(
        openNavigationDrawer = openNavigationDrawer,
        fieldsEditable = presenter.fieldsEditable.collectAsState().value,
        name = name,
        onNameChange = { presenter.name.value = it },
        nameFeedback = presenter.name.collectFeedbackAsState().value,
        age = age,
        onAgeChange = { presenter.age.value = it.toIntOrNull() },
        ageFeedback = presenter.age.collectFeedbackAsState().value,
        licenseType = presenter.licenseType.collectValueAsState().value,
        onLicenseTypeChange = { presenter.licenseType.value = it },
        licenseTypeFeedback = presenter.licenseType.collectFeedbackAsState().value,
        displayServiceSettings = { serviceSettingsDisplayed = true },
        performReset = performReset,
        resetButtonEnabled = presenter.canResetFlow.collectAsState(false).value,
        applyButtonEnabled = presenter.canApplyFlow.collectAsState(true).value,
        onApplyButtonClicked = { coroutineScope.launch { presenter.submitApplication() } },
        processingApplication = presenter.processingApplication.collectAsState().value,
        focusFirstFocusRequester = focusFirstError
    )
}

@Composable
fun DriversLicenseApplicationFormContent(
    openNavigationDrawer: () -> Unit,
    fieldsEditable: Boolean,
    name: MutableState<String>,
    onNameChange: (String) -> Unit,
    nameFeedback: List<DriversLicenseApplicationModelFeedback>,
    age: MutableState<String>,
    onAgeChange: (String) -> Unit,
    ageFeedback: List<DriversLicenseApplicationModelFeedback>,
    licenseType: LicenseType?,
    onLicenseTypeChange: (LicenseType) -> Unit,
    licenseTypeFeedback: List<DriversLicenseApplicationModelFeedback>,
    displayServiceSettings: () -> Unit,
    performReset: () -> Unit,
    resetButtonEnabled: Boolean,
    applyButtonEnabled: Boolean,
    onApplyButtonClicked: () -> Unit,
    processingApplication: Boolean,
    focusFirstFocusRequester: FocusFirstFocusRequester<DriversLicenseApplicationModel>
) {
    Column {
        ConerTopLevelTopAppBar(
            title = { Text(strings.driversLicenseApplicationHeading) },
            navigationIcon = {
                IconButton(
                    onClick = openNavigationDrawer
                ) {
                    Icon(Icons.Default.Menu, contentDescription = strings.menuContentDescription)
                }
            },
            actions = {
                IconButton(
                    onClick = displayServiceSettings,
                    modifier = Modifier
                        .testTag("driversLicenseApplicationServiceSettingsButton")
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Filled.Settings),
                        contentDescription = strings.driversLicenseApplicationServiceSettings,
                        modifier = Modifier
                            .testTag("driversLicenseApplicationServiceSettingsIcon")
                    )
                }
                IconButton(
                    onClick = performReset,
                    enabled = resetButtonEnabled,
                    modifier = Modifier
                        .testTag("resetButton")
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.Undo),
                        contentDescription = strings.driversLicenseApplicationFormReset,
                        modifier = Modifier
                            .testTag("resetButtonIcon"),
                    )
                }
            }
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedCard(
                modifier = Modifier
                    .width(432.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row {
                        OutlinedTextField(
                            value = name.value,
                            label = {
                                Text(
                                    text = strings.driversLicenseNameField,
                                    modifier = Modifier
                                        .testTag("nameField")
                                )
                            },
                            singleLine = true,
                            onValueChange = {
                                name.value = it
                                onNameChange(it)
                            },
                            isError = nameFeedback.isInvalid,
                            supportingText = ValidationFeedbackSupportingText(nameFeedback, "name"),
                            readOnly = !fieldsEditable,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusOnError(focusFirstFocusRequester, DriversLicenseApplicationModel::name)
                                .testTag("name")
                        )
                    }
                    Row {
                        OutlinedTextField(
                            value = age.value,
                            label = {
                                Text(
                                    text = strings.driversLicenseAgeField,
                                    modifier = Modifier
                                        .testTag("ageField")
                                )
                            },
                            singleLine = true,
                            onValueChange = {
                                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                                    age.value = it
                                    onAgeChange(it)
                                }
                            },
                            isError = ageFeedback.isInvalid,
                            supportingText = ValidationFeedbackSupportingText(ageFeedback, "age"),
                            readOnly = !fieldsEditable,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusOnError(focusFirstFocusRequester, DriversLicenseApplicationModel::age)
                                .testTag("age")
                        )
                    }
                    Row {
                        var expanded by remember { mutableStateOf(false) }
                        val guardedMutateExpanded: (Boolean) -> Unit = {
                            if (fieldsEditable || !it) expanded = it
                        }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = guardedMutateExpanded,
                            modifier = Modifier
                                .testTag("licenseTypeDropdownMenuBox")
                        ) {
                            OutlinedTextField(
                                value = licenseType.let { strings.getNullable(it) },
                                label = {
                                    Text(
                                        text = strings.driversLicenseLicenseTypeField,
                                        modifier = Modifier
                                            .testTag("licenseTypeField")
                                    )
                                },
                                readOnly = true,
                                onValueChange = { },
                                isError = licenseTypeFeedback.isInvalid,
                                supportingText = ValidationFeedbackSupportingText(licenseTypeFeedback, "licenseType"),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                                    .onKeyEvent {
                                        when {
                                            it.key == Key.DirectionDown -> { guardedMutateExpanded(true); true }
                                            else -> false
                                        }
                                    }
                                    .focusOnError(focusFirstFocusRequester, DriversLicenseApplicationModel::licenseType)
                                    .testTag("licenseType")
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { guardedMutateExpanded(false) },
                                modifier = Modifier
                                    .testTag("licenseTypeDropdownMenu")
                            ) {
                                strings.licenseTypeLabels.forEach { pair ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = pair.second,
                                                modifier = Modifier
                                                    .testTag("licenseTypeDropdownMenuItemText")
                                            )
                                        },
                                        onClick = {
                                            guardedMutateExpanded(false)
                                            onLicenseTypeChange(pair.first)
                                        },
                                        modifier = Modifier
                                            .testTag("licenseTypeDropdownMenuItem")
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = onApplyButtonClicked,
                            enabled = applyButtonEnabled,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .testTag("applyButton")
                        ) {
                            Row {
                                if (processingApplication) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .align(Alignment.CenterVertically)
                                            .testTag("applyButtonProgress")
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .width(8.dp)
                                    )
                                }
                                Text(
                                    text = strings.driversLicenseApplicationFormApply,
                                    modifier = Modifier
                                        .testTag("applyButtonText")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun DriversLicenseApplicationFormPreview() {
    withDI(di) {
        ConerTheme {
            Scaffold {
                DriversLicenseApplicationFormContent(
                    openNavigationDrawer= {},
                    fieldsEditable = true,
                    name = mutableStateOf(""),
                    onNameChange = {},
                    nameFeedback = emptyList(),
                    age = mutableStateOf(""),
                    onAgeChange = {},
                    ageFeedback = emptyList(),
                    licenseType = null,
                    onLicenseTypeChange = {},
                    licenseTypeFeedback = emptyList(),
                    displayServiceSettings = {},
                    performReset = {},
                    resetButtonEnabled = false,
                    applyButtonEnabled = true,
                    onApplyButtonClicked = {},
                    processingApplication = false,
                    FocusFirstFocusRequester(
                        listOf(
                            DriversLicenseApplicationModel::name,
                            DriversLicenseApplicationModel::age,
                            DriversLicenseApplicationModel::licenseType
                        )
                    )
                )
            }
        }
    }
}
