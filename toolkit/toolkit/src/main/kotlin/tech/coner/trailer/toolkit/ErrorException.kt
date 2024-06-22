package tech.coner.trailer.toolkit

class ErrorException(val error: Error, cause: Throwable? = null) : Exception(cause) {
}