package tech.coner.trailer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeCloseable
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import tech.coner.snoozle.db.session.data.DataSession
import tech.coner.trailer.io.util.Cache

interface DataSessionHolder : ScopeCloseable,
    CoroutineScope {
    val environment: EnvironmentHolder
    var registry: ScopeRegistry?
}

open class DataSessionHolderImpl(
    di: DI,
    override val environment: EnvironmentHolder
) : DataSessionHolder,
    DIAware by di,
    CoroutineScope by CoroutineScope(Dispatchers.Default)
{
    override var registry: ScopeRegistry? = null
    override val diContext = diContext { this }

    private val dataSession: DataSession by instance()
    private val caches: List<Cache<*, *>> by allInstances()

    override fun close() {
        dataSession.close()
            .onFailure { throw Exception("Failed to close data session", it) }
        runBlocking { caches.forEach { it.clear() } }
        registry?.clear()
        cancel()
    }
}

object DataSessionScope : Scope<DataSessionHolder> {
    override fun getRegistry(context: DataSessionHolder): ScopeRegistry {
        return context.registry
            ?: StandardScopeRegistry().also { context.registry = it }
    }
}