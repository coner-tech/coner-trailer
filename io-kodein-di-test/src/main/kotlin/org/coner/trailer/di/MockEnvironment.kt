package org.coner.trailer.di

import org.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import org.coner.trailer.io.DatabaseConfiguration
import org.kodein.di.bindings.ScopeRegistry

class MockEnvironmentHolder : EnvironmentHolder {

    override var registry: ScopeRegistry? = null

    override val configurationServiceArgument: ConfigurationServiceArgument
        get() = throw UnsupportedOperationException()
    override val databaseConfiguration: DatabaseConfiguration?
        get() = throw UnsupportedOperationException()
    override val motorsportRegCredentialSupplier: () -> MotorsportRegBasicCredentials
        get() = throw UnsupportedOperationException()

    override fun requireDatabaseConfiguration(): DatabaseConfiguration {
        throw UnsupportedOperationException()
    }

    override fun openDataSession(): DataSessionHolder {
        return MockDataSessionHolder(this)
    }
}