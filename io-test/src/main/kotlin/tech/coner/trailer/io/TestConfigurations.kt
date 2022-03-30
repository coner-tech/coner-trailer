package tech.coner.trailer.io

import java.nio.file.Path

class TestConfigurations(
    private val root: Path
) {

    val testDatabaseConfigurations = TestDatabaseConfigurations(root)

    fun testConfiguration(): Configuration {
        return Configuration(
            databases = testDatabaseConfigurations.allByName,
            defaultDatabaseName = testDatabaseConfigurations.bar.name
        )
    }
}