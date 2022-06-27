package tech.coner.trailer.io

import java.nio.file.Path
import kotlin.io.path.createDirectories

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
    val allByName = all.associateBy { it.name }

    val noDatabase = factory(
        name = "no database",
        motorsportReg = null
    )

    private fun factory(name: String, motorsportReg: DatabaseConfiguration.MotorsportReg?, default: Boolean = false): DatabaseConfiguration {
        fun path(type: String) = root
            .resolve(name)
            .resolve(type)
            .also { it.createDirectories() }
        return DatabaseConfiguration(
            name = name,
            crispyFishDatabase = path("crispy-fish"),
            snoozleDatabase = path("snoozle"),
            motorsportReg = motorsportReg,
            default = default
        )
    }
}