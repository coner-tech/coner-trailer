package tech.coner.trailer.cli.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

object CliCoroutineScope : CoroutineScope {
    override val coroutineContext = Dispatchers.Main + Job()
}