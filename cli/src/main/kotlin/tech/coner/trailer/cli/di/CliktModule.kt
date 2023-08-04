package tech.coner.trailer.cli.di

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.util.CliBackgroundCoroutineScope
import tech.coner.trailer.cli.util.CliMainCoroutineScope

val cliktModule = DI.Module("coner.trailer.cli.clikt") {
        bindSingleton { CliMainCoroutineScope(CoroutineScope(Dispatchers.Main) + CoroutineName("CLI main scope")) }
        bindSingleton { CliBackgroundCoroutineScope(CoroutineScope(Dispatchers.Default) + CoroutineName("CLI background scope")) }
}