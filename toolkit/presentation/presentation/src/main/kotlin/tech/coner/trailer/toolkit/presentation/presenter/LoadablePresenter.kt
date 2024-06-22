package tech.coner.trailer.toolkit.presentation.presenter

import arrow.core.Either
import kotlinx.coroutines.Deferred

interface LoadablePresenter<ERROR, ITEM> {

    suspend fun load(): Deferred<Result<Either<ERROR, ITEM>>>
}