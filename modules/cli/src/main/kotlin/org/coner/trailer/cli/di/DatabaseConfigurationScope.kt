package org.coner.trailer.cli.di

import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import java.util.*

object DatabaseConfigurationScope : Scope<DatabaseConfiguration> {

    private val scopes = WeakHashMap<DatabaseConfiguration, ScopeRegistry>()

    override fun getRegistry(context: DatabaseConfiguration): ScopeRegistry {
        return scopes[context]
                ?: StandardScopeRegistry().also {
                    scopes[context] = it
                }
    }
}