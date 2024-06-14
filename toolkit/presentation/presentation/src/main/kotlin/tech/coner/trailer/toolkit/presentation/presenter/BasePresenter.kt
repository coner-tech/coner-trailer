package tech.coner.trailer.toolkit.presentation.presenter

abstract class BasePresenter<ARGUMENT : Presenter.Argument> : Presenter<ARGUMENT> {

    protected abstract val argument: ARGUMENT
}