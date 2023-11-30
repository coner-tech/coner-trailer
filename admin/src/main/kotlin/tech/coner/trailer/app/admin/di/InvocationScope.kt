package tech.coner.trailer.app.admin.di

import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry

object InvocationScope : Scope<Invocation> {
    override fun getRegistry(context: Invocation): ScopeRegistry {
        return context.scopeRegistry
            ?: StandardScopeRegistry().also { context.scopeRegistry = it }
    }
}

class Invocation {
    var scopeRegistry: ScopeRegistry? = null
}