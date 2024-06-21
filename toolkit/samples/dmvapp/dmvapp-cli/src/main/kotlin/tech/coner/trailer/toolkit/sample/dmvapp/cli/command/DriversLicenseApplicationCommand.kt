package tech.coner.trailer.toolkit.sample.dmvapp.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import tech.coner.trailer.toolkit.sample.dmvapp.cli.view.DriversLicenseView
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Localization
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter.DriversLicenseApplicationPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.util.dashify
import tech.coner.trailer.toolkit.validation.ValidationResult

class DriversLicenseApplicationCommand(
    private val presenter: DriversLicenseApplicationPresenter,
    private val localization: Localization,
    private val driversLicenseView: DriversLicenseView,
) : CliktCommand(
    name = "drivers-license-application"
), CoroutineScope by CoroutineScope(Dispatchers.Default + CoroutineName("DriversLicenseApplicationCommand")) {

    private val name: String by option()
        .required()
    private val age: Int by option()
        .int()
        .required()
    private val licenseType: LicenseType by option()
        .choice(localization.licenseTypeLabels.associate { it.second.dashify() to it.first })
        .required()

    override fun run() {
        val model = presenter.state.model
        model.name = name
        model.age = age
        model.licenseType = licenseType
        model.commit()
            .onFailure {
                it.feedback.echo()
                throw Abort()
            }
        runBlocking { presenter.processApplication() }
            ?.also {
                echo(localization.driversLicenseGranted)
                echo(driversLicenseView(it.driversLicense!!))
            }
    }

    private fun ValidationResult<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>.echo() {
        if (feedback.isEmpty()) return
        feedback
            .map { (_, feedbacks) ->
                feedbacks.joinToString(separator = System.lineSeparator()) { "[${it.severity.name.lowercase()}]: ${localization.label(it)}" }
            }
            .forEach { echo(it, err = true) }
    }
}