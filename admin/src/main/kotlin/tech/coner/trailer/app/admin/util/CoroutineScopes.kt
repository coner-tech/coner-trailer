package tech.coner.trailer.app.admin.util

import kotlinx.coroutines.CoroutineScope

class CliMainCoroutineScope(coroutineScope: CoroutineScope) : CoroutineScope by coroutineScope
class CliBackgroundCoroutineScope(coroutineScope: CoroutineScope) : CoroutineScope by coroutineScope