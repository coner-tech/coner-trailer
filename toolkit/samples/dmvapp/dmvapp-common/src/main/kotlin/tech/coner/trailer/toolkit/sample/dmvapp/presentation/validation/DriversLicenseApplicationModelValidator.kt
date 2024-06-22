package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.DriversLicenseApplicationModelAdapters
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.*
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.input

typealias DriversLicenseApplicationModelValidator = Validator<Unit, DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>

fun DriversLicenseApplicationModelValidator(
    driversLicenseClerk: DriversLicenseClerk,
    driversLicenseApplicationModelAdapters: DriversLicenseApplicationModelAdapters,
): DriversLicenseApplicationModelValidator = Validator {

    DriversLicenseApplicationModel::name { if (it.isNullOrBlank()) NameIsRequired else null }
    DriversLicenseApplicationModel::age { if (it == null) AgeIsRequired else null }
    DriversLicenseApplicationModel::licenseType { licenseType -> LicenseTypeIsRequired.takeIf { licenseType == null } }
    returnEarlyIfAny { it.severity == Severity.Error }
    input(
        validator = driversLicenseClerk,
        mapInputFn = { driversLicenseApplicationModelAdapters.toDomainEntity(it).getOrThrow() },
        mapFeedbackObjectFn = { DelegatedFeedback(it) }
    )
}
