package tech.coner.trailer.cli.util

import kotlinx.coroutines.CoroutineScope

class CliMainCoroutineScope(coroutineScope: CoroutineScope) : CoroutineScope by coroutineScope
class CliBackgroundCoroutineScope(coroutineScope: CoroutineScope) : CoroutineScope by coroutineScope