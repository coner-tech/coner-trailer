package tech.coner.trailer.presentation.library.presenter

abstract class BasePresenter<ARGUMENT : Presenter.Argument> : Presenter<ARGUMENT> {

    protected abstract val argument: ARGUMENT
}