package org.coner.trailer.cli.io

import java.nio.file.Files
import java.nio.file.Path

class TestDatabaseConfigurations(
        private val root: Path
) {

    val foo = factory(name = "foo", motorsportReg = null)
    val bar = factory(
            name = "bar",
            motorsportReg = DatabaseConfiguration.MotorsportReg(
                    username = "user@bar",
                    organizationId = "bar.organizationId"
            ),
            default = true
    )
    val all = listOf(bar, foo)
    val allByName = all.map { it.name to it }.toMap()

    val noDatabase = factory(
            name = "no database",
            motorsportReg = null
    )

    private fun factory(name: String, motorsportReg: DatabaseConfiguration.MotorsportReg?, default: Boolean = false): DatabaseConfiguration {
        fun path(type: String) = root
                .resolve(type)
                .resolve(name)
                .also { Files.createDirectories(it) }
        return DatabaseConfiguration(
                name = name,
                crispyFishDatabase = path("crispy-fish"),
                snoozleDatabase = path("snoozle"),
                motorsportReg = motorsportReg,
                default = default
        )
    }
}