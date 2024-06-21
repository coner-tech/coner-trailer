package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import tech.coner.trailer.toolkit.presentation.presenter.SecondDraftPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.toDomainEntity
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.state.DriversLicenseApplicationState
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelValidator

class DriversLicenseApplicationPresenter(
    override val di: DI,
    initialState: DriversLicenseApplicationState? = null,
) : SecondDraftPresenter<DriversLicenseApplicationState>(), DIAware {

    private val service: DriversLicenseApplicationService by instance()
    private val validator: DriversLicenseApplicationModelValidator by instance()

    override val initialState = initialState
        ?: DriversLicenseApplicationState(
            model = DriversLicenseApplicationItemModel(
                validator = validator
            )
        )

    suspend fun processApplication(): DriversLicenseApplication.Outcome? {
        return state.model.item.toDomainEntity()
            ?.let { service.process(it) }
            ?.also { outcome -> update { it.copy(outcome = outcome) } }
    }
}