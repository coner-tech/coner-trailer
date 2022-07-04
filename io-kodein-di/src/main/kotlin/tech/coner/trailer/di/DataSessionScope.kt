package tech.coner.trailer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeCloseable
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.snoozle.db.session.data.DataSession
import kotlin.coroutines.CoroutineContext

interface DataSessionHolder : ScopeCloseable,
    CoroutineScope {
    val environment: EnvironmentHolder
    var registry: ScopeRegistry?
}

open class DataSessionHolderImpl(
    di: DI,
    override val environment: EnvironmentHolder
) : DataSessionHolder, DIAware by di {
    override var registry: ScopeRegistry? = null
    override val diContext = diContext { this }

    override val coroutineContext = Dispatchers.IO + Job()

    private val dataSession: DataSession by instance()

    override fun close() {
        dataSession.close()
            .onFailure { throw Exception("Failed to close data session", it) }
        registry?.clear()
        coroutineContext.cancel()
    }
}

object DataSessionScope : Scope<DataSessionHolder> {
    override fun getRegistry(context: DataSessionHolder): ScopeRegistry {
        return context.registry
            ?: StandardScopeRegistry().also { context.registry = it }
    }
}