package tech.coner.trailer.presentation.presenter

abstract class BasePresenter<ARGUMENT : Presenter.Argument> : Presenter<ARGUMENT> {

    protected abstract val argument: ARGUMENT

    /**
     * Process the contents of the argument to prepare the initial state of the presenter
     */
    protected abstract fun processArgument()
}