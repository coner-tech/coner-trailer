package tech.coner.trailer.toolkit.presentation.presenter

interface ItemModelPresenter {

    suspend fun reset()

    suspend fun commit()

    suspend fun validate()
}