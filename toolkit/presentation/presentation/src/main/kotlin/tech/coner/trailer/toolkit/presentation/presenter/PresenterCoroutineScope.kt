package tech.coner.trailer.toolkit.presentation.presenter

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class PresenterCoroutineScope(coroutineScope: CoroutineScope) : CoroutineScope by coroutineScope {

    constructor(coroutineContext: CoroutineContext) : this(CoroutineScope(coroutineContext))
}
