package tech.coner.trailer.app.admin.util

inline fun <T> Result<T>.succeedOrThrow(successFn: (T) -> Unit) {
    onSuccess(successFn)
    onFailure { throw it }
}