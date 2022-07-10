package tech.coner.trailer.di

import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeCloseable
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry

object EventResultsSessionScope : Scope<EventResultsSession> {
    override fun getRegistry(context: EventResultsSession): ScopeRegistry {
        return context.registry
            ?: StandardScopeRegistry().also { context.registry = it }
    }
}

class EventResultsSession : ScopeCloseable {
    var registry: ScopeRegistry? = null

    override fun close() {
        registry?.clear()
    }
}
