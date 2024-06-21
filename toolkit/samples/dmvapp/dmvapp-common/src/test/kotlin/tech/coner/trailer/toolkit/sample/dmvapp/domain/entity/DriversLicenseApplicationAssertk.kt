package tech.coner.trailer.toolkit.sample.dmvapp.domain.entity

import assertk.Assert
import assertk.assertions.prop

fun Assert<DriversLicenseApplication.Outcome>.driversLicense() = prop(DriversLicenseApplication.Outcome::driversLicense)
fun Assert<DriversLicenseApplication.Outcome>.feedback() = prop(DriversLicenseApplication.Outcome::feedback)
