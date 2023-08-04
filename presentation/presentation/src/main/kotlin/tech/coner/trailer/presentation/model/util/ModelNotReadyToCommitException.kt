package tech.coner.trailer.presentation.model.util

class ModelNotReadyToCommitException(cause: Throwable) : Throwable(
    message = "Model was not ready to commit"
)
