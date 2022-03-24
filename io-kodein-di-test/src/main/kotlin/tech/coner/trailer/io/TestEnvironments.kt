package tech.coner.trailer.io

import org.kodein.di.DI
import tech.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.EnvironmentHolderImpl
import tech.coner.trailer.di.MockEnvironmentHolder
import java.nio.file.Path
import kotlin.io.path.createDirectory

object TestEnvironments {

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