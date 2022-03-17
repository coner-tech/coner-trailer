package tech.coner.trailer.io

import tech.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.EnvironmentHolderImpl
import tech.coner.trailer.di.MockEnvironmentHolder
import org.kodein.di.DI
import java.nio.file.Path
import kotlin.io.path.createDirectory

object TestEnvironments {
    fun minimal(di: DI) = EnvironmentHolderImpl(
        di = di,
        configurationServiceArgument = ConfigurationServiceArgument.Default,
        databaseConfiguration = null,
        motorsportRegCredentialSupplier = { throw UnsupportedOperationException() }
    )

    fun temporary(
        di: DI,
        root: Path,
        databaseConfiguration: DatabaseConfiguration,
        motorsportRegCredentialSupplier: (() -> MotorsportRegBasicCredentials)? = null
    ) = EnvironmentHolderImpl(
        di = di,
        configurationServiceArgument = ConfigurationServiceArgument.Override(
            configDir = root.resolve("config").createDirectory()
        ),
        databaseConfiguration = databaseConfiguration,
        motorsportRegCredentialSupplier = motorsportRegCredentialSupplier
            ?: { throw UnsupportedOperationException() }
    )
    
    fun mock() = MockEnvironmentHolder()
}