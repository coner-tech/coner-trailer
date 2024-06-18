package tech.coner.trailer.toolkit.sample.dmvapp.presentation

import tech.coner.trailer.toolkit.presentation.presenter.SecondDraftPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.DriversLicenseApplicationServiceImpl
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.toDomainEntity
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.state.DriversLicenseApplicationState
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelValidator

class DriversLicenseApplicationPresenter(
    initialState: DriversLicenseApplicationState? = null,
    private val service: DriversLicenseApplicationService = DriversLicenseApplicationServiceImpl()
) : SecondDraftPresenter<DriversLicenseApplicationState>() {

    override val initialState = initialState
        ?: DriversLicenseApplicationState(
            model = DriversLicenseApplicationItemModel(
                validator = DriversLicenseApplicationModelValidator()
            )
        )

    suspend fun processApplication(): DriversLicenseApplication.Outcome? {
        return state.model.item.toDomainEntity()
            ?.let { service.process(it) }
            ?.also { outcome -> update { it.copy(outcome = outcome) } }
    }
}