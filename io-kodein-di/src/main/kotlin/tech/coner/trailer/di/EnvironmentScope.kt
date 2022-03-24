package tech.coner.trailer.di

import tech.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import tech.coner.trailer.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import tech.coner.trailer.io.Configuration


interface EnvironmentHolder {
    var registry: ScopeRegistry?
    val configurationServiceArgument: ConfigurationServiceArgument
    val configuration: Configuration?
    val databaseConfiguration: DatabaseConfiguration?
    val motorsportRegCredentialSupplier: () -> MotorsportRegBasicCredentials
    fun openDataSession(): DataSessionHolder
    fun requireConfiguration() = requireNotNull(configuration) { "Missing configuration" }
    fun requireDatabaseConfiguration() = requireNotNull(databaseConfiguration) { "Missing databaseConfiguration "}
}

class EnvironmentHolderImpl(
    di: DI,
    override val configurationServiceArgument: ConfigurationServiceArgument,
    override val configuration: Configuration?,
    override val databaseConfiguration: DatabaseConfiguration?,
    override val motorsportRegCredentialSupplier: () -> MotorsportRegBasicCredentials
) : EnvironmentHolder, DIAware by di {

    override fun openDataSession(): DataSessionHolder {
        return DataSessionHolderImpl(di, this)
    }

    override var registry: ScopeRegistry? = null
}

object EnvironmentScope : Scope<EnvironmentHolder> {
    override fun getRegistry(context: EnvironmentHolder): ScopeRegistry {
        return context.registry
            ?: StandardScopeRegistry().also { context.registry = it }
    }
}