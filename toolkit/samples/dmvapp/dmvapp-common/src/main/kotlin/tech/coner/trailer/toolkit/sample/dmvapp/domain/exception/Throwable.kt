package tech.coner.trailer.toolkit.sample.dmvapp.domain.exception

import kotlin.Throwable

abstract class Throwable(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)
