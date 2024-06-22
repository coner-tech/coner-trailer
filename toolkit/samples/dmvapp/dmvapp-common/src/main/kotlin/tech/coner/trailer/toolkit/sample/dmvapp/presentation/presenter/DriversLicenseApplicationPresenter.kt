package tech.coner.trailer.toolkit.sample.dmvapp.presentation.presenter

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import tech.coner.trailer.toolkit.presentation.presenter.ItemModelPresenter
import tech.coner.trailer.toolkit.presentation.presenter.StatefulPresenter
import tech.coner.trailer.toolkit.presentation.state.StateContainer
import tech.coner.trailer.toolkit.presentation.state.mutableItemModelProperty
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplicationRejection
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.DriversLicenseApplicationService
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.adapter.DriversLicenseApplicationModelAdapters
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationItemModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.model.DriversLicenseApplicationRejectionModel
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.state.DriversLicenseApplicationState
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelFeedback
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.validation.DriversLicenseApplicationModelValidator
import tech.coner.trailer.toolkit.validation.ValidationOutcome

class DriversLicenseApplicationPresenter(
    private val initialState: DriversLicenseApplicationState? = null,
    private val adapters: DriversLicenseApplicationModelAdapters,
    private val service: DriversLicenseApplicationService,
    private val validator: DriversLicenseApplicationModelValidator,
    private val strings: Strings,
) : StatefulPresenter<DriversLicenseApplicationState>,
    ItemModelPresenter {

    private val stateContainer = StateContainer(
        initialState = initialState ?: DriversLicenseApplicationState(
            itemModel = DriversLicenseApplicationItemModel(
                validator = validator
            )
        ),
    )
    override val state: DriversLicenseApplicationState get() = stateContainer.state
    override val stateFlow: StateFlow<DriversLicenseApplicationState> get() = stateContainer.stateFlow

    suspend fun submitApplication(): Result<Either<DriversLicenseApplicationRejectionModel, DriversLicense>> = coroutineScope {
        runCatching {
            either {
                if (state.processing) cancel()
                stateContainer.update {
                    it.copy(processing = true)
                }
                either {
                    state.itemModel.commit()
                        .mapLeft { DriversLicenseApplicationRejectionModel.Invalid(it) }
                        .bind()
                }
                    .map {
                        service.submit(adapters.toDomainEntity(it).getOrThrow()).getOrThrow()
                            .mapLeft { rejection ->
                                when (rejection) {
                                    is DriversLicenseApplicationRejection.Invalid -> DriversLicenseApplicationRejectionModel.Invalid(
                                        adapters.domainEntityValidationAdapter(rejection.validationOutcome)
                                    )
                                    is DriversLicenseApplicationRejection.Sassed -> DriversLicenseApplicationRejectionModel.Sassed(
                                        rejection.sass
                                    )
                                    is DriversLicenseApplicationRejection.LegallyProhibited -> DriversLicenseApplicationRejectionModel.LegallyProhibited
                                }

                            }
                            .bind()
                    }
                    .bind()
            }
        }
            .also { result -> stateContainer.update { it.copy(processing = false, outcome = result) } }
    }

    suspend fun clearOutcome() {
        stateContainer.update { it.copy(outcome = null) }
    }

    override suspend fun reset() {
        stateContainer.update {
            initialState
                ?: DriversLicenseApplicationState(
                    itemModel = DriversLicenseApplicationItemModel(
                        validator = validator
                    )
                )
        }
    }

    override suspend fun commit() {
        state.itemModel.commit()
    }

    override suspend fun validate() {
        state.itemModel.validate()
    }

    val validationResult get() = state.itemModel.pendingItemValidation
    val validationOutcomeFlow: Flow<ValidationOutcome<DriversLicenseApplicationModel, DriversLicenseApplicationModelFeedback>> get() = stateFlow
        .flatMapLatest { it.itemModel.pendingItemValidationFlow }

    val name = stateContainer.mutableItemModelProperty(
        getFn = { name },
        updateFn = { copy(name = it) },
        property = DriversLicenseApplicationModel::name
    )

    val age = stateContainer.mutableItemModelProperty(
        getFn = { age },
        updateFn = { copy(age = it) },
        property = DriversLicenseApplicationModel::age
    )

    val licenseType = stateContainer.mutableItemModelProperty(
        getFn = { licenseType },
        updateFn = { copy(licenseType = it) },
        property = DriversLicenseApplicationModel::licenseType
    )

    val processingApplication = stateContainer.mutableStateProperty(
        getValue = { processing },
        updateState = { copy(processing = it) }
    )
    val fieldsEditable = stateContainer.stateProperty { fieldsEditable }

    val canResetFlow: Flow<Boolean> by lazy {
        stateFlow
            .flatMapLatest { it.itemModel.canResetFlow }
            .combine(processingApplication.flow) { itemModelCanReset, processing ->
                itemModelCanReset && !processing
            }
            .distinctUntilChanged()
    }

    val canApplyFlow: Flow<Boolean> = stateFlow
        .combine(
            stateFlow.flatMapLatest { it.itemModel.pendingItemValidationFlow }
        ) { state, _ ->
            state.canApply
        }
}