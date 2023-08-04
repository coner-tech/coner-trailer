package tech.coner.trailer.io.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope

suspend inline fun <Scope : CoroutineScope, T> Scope.runSuspendCatching(block: Scope.() -> T): Result<T> {
    return runCatching(block)
        .onFailure { if (it is CancellationException) throw it }
}
