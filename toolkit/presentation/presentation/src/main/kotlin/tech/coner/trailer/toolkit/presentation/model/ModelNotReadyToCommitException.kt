package tech.coner.trailer.toolkit.presentation.model

class ModelNotReadyToCommitException(cause: Throwable? = null) : Throwable(
    message = "Model was not ready to commit"
)
