package tech.coner.trailer.cli.util

inline fun <T> Result<T>.succeedOrThrow(successFn: (T) -> Unit) {
    onSuccess(successFn)
    onFailure { throw it }
}