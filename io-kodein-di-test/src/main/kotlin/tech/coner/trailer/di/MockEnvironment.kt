package tech.coner.trailer.di

import org.kodein.di.bindings.ScopeRegistry
import tech.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration

class MockEnvironmentHolder : EnvironmentHolder {

    override var registry: ScopeRegistry? = null

    override val configurationServiceArgument: ConfigurationServiceArgument
        get() = throw UnsupportedOperationException()
    override val configuration: Configuration?
        get() = throw UnsupportedOperationException()
    override val databaseConfiguration: DatabaseConfiguration?
        get() = throw UnsupportedOperationException()
    override val motorsportRegCredentialSupplier: () -> MotorsportRegBasicCredentials
        get() = throw UnsupportedOperationException()

    override fun requireConfiguration(): Configuration {
        throw UnsupportedOperationException()
    }

    override fun requireDatabaseConfiguration(): DatabaseConfiguration {
        throw UnsupportedOperationException()
    }

    override fun openDataSession(): DataSessionHolder {
        return MockDataSessionHolder(this)
    }
}