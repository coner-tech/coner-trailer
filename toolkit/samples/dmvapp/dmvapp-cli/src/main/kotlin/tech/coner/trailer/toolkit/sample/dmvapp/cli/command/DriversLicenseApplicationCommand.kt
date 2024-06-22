package tech.coner.trailer.toolkit.sample.dmvapp.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.float
import com.github.ajalt.clikt.parameters.types.int
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.coner.trailer.toolkit.dashify
import tech.coner.trailer.toolkit.sample.dmvapp.cli.view.DriversLicenseView
import tech.coner.trailer.toolkit.sample.dmvapp.cli.view.ValidationResultView
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.DriversLicenseApplicationServiceImpl
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationRejectionModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.DriversLicenseApplicationPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.validation.ValidationOutcome

class DriversLicenseApplicationCommand(
    private val presenter: DriversLicenseApplicationPresenter,
    private val strings: Strings,
    private val driversLicenseView: DriversLicenseView,
    private val service: DriversLicenseApplicationServiceImpl,
) : CliktCommand(
    name = "drivers-license-application"
), CoroutineScope by CoroutineScope(Dispatchers.Default + CoroutineName("DriversLicenseApplicationCommand")) {

    private val name: String by option()
        .required()
    private val age: Int by option()
        .int()
        .required()
    private val licenseType: LicenseType by option()
        .choice(strings.licenseTypeLabels.associate { it.second.dashify() to it.first })
        .required()

    private val buildingOnFireChance: Float? by option()
        .float()
    private val sassChance: Float? by option()
        .float()
    private val legallyProhibitedChance: Float? by option()
        .float()

    override fun run(): Unit = runBlocking {
        presenter.name.value = name
        presenter.age.value = age
        presenter.licenseType.value = licenseType

        buildingOnFireChance?.also { service.buildingOnFireChance = it }
        sassChance?.also { service.sassChance = it }
        legallyProhibitedChance?.also { service.legallyProhibitedChance = it }

        val processing = async {
            val progress = launch {
                echo("Processing...", trailingNewline = false)
                while (isActive) {
                    echo('.', trailingNewline = false)
                    delay(Random.nextInt(100, 1000).milliseconds)
                }
            }
            presenter.submitApplication()
                .also { progress.cancel() }
        }

        processing.await().getOrThrow()
            .also { echo() }
            .onLeft { rejection ->
                when (rejection) {
                    is DriversLicenseApplicationRejectionModel.Invalid -> rejection.validationOutcome.widget()
                    is DriversLicenseApplicationRejectionModel.Sassed -> strings[rejection.sass]
                    is DriversLicenseApplicationRejectionModel.LegallyProhibited -> strings.driversLicenseApplicationRejectionLegallyProhibited
                }
                    .also { echo(terminal.render(it), err = true) }
            }
            .onRight {
                echo(strings.driversLicenseGranted)
                echo(driversLicenseView(it))
            }
    }

    private fun ValidationOutcome<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>.widget() =
        ValidationResultView<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>(
            terminal = terminal,
            strings = strings,
            fieldStringsMap = mapOf(
                DriversLicenseApplicationModel::name to strings.driversLicenseNameField,
                DriversLicenseApplicationModel::age to strings.driversLicenseAgeField,
                DriversLicenseApplicationModel::licenseType to strings.driversLicenseLicenseTypeField,
            ),
            messageFn = { strings[it] }
        ).invoke(this)
}