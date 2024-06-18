package tech.coner.trailer.toolkit.sample.dmvapp.presentation.state

import tech.coner.trailer.toolkit.presentation.state.State
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationItemModel

data class DriversLicenseApplicationState(
    val model: DriversLicenseApplicationItemModel,
    val outcome: DriversLicenseApplication.Outcome? = null
) : State
