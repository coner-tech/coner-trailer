package tech.coner.trailer.io.service

/**
 * Something went wrong while trying to read data
 */
class ReadException(override val message: String, cause: Throwable? = null) : RuntimeException(message, cause)
