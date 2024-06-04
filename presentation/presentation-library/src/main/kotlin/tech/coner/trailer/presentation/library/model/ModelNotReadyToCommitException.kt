package tech.coner.trailer.presentation.library.model

class ModelNotReadyToCommitException(cause: Throwable? = null) : Throwable(
    message = "Model was not ready to commit"
)
