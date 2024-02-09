package tech.coner.trailer.presentation.library.model

class ModelNotReadyToCommitException(cause: Throwable) : Throwable(
    message = "Model was not ready to commit"
)
