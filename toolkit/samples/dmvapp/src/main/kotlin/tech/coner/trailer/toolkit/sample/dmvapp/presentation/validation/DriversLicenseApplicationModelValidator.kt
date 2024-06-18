package tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.validation.DriversLicenseClerk
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.toDomainEntity
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.AgeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.DelegatedFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.LicenseTypeIsRequired
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback.NameIsRequired
import tech.coner.trailer.toolkit.validation.Severity
import tech.coner.trailer.toolkit.validation.Validator
import tech.coner.trailer.toolkit.validation.input

typealias DriversLicenseApplicationModelValidator = Validator<Unit, DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>

fun DriversLicenseApplicationModelValidator(
    driversLicenseClerk: DriversLicenseClerk = DriversLicenseClerk()
): DriversLicenseApplicationModelValidator = Validator {
    DriversLicenseApplicationModel::name { if (it == null) NameIsRequired else null }
    DriversLicenseApplicationModel::age { if (it == null) AgeIsRequired else null }
    DriversLicenseApplicationModel::licenseType { licenseType -> LicenseTypeIsRequired.takeIf { licenseType == null } }
    returnEarlyIfAny { it.severity == Severity.Error }
    input(
        validator = driversLicenseClerk,
        mapInputFn = { it.toDomainEntity()!! },
        mapFeedbackKeys = mapOf(
            DriversLicenseApplication::name to DriversLicenseApplicationModel::name,
            DriversLicenseApplication::age to DriversLicenseApplicationModel::age,
            DriversLicenseApplication::licenseType to DriversLicenseApplicationModel::licenseType
        ),
        mapFeedbackObjectFn = { DelegatedFeedback(it) }
    )
}
