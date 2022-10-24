package tech.coner.trailer.io

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.fail
import java.net.ServerSocket
import java.nio.file.Path

class TestConfigurations(
    private val root: Path
) {

    val testDatabaseConfigurations = TestDatabaseConfigurations(root)

    fun testConfiguration(): Configuration {
        return Configuration(
            databases = testDatabaseConfigurations.allByName,
            defaultDatabaseName = testDatabaseConfigurations.bar.name,
            webapps = Configuration.Webapps(
                results = WebappConfiguration(
                    port = randomAvailablePort(),
                    exploratory = true
                )
            )
        )
    }

    private fun randomAvailablePort() = try {
        val socket = ServerSocket(0)
        assertThat(socket.localPort).isGreaterThan(0)
        socket.localPort
    } catch (t: Throwable) {
        fail(
            message = "Unable to allocate a random available port"
        )
    }
}