package org.coner.trailer.cli.io

import java.io.File

class TestDatabaseConfigurations(
        private val root: File
) {

    val foo = factory(name = "foo")
    val bar = factory(name = "bar", default = true)
    val all = listOf(foo, bar)
    val allByName = all.map { it.name to it }.toMap()

    private fun factory(name: String, default: Boolean = false): DatabaseConfiguration {
        fun dir(type: String) = root
                .resolve(type)
                .resolve(name)
                .also {
                    it.mkdirs()
                    it.deleteOnExit()
                }
        return DatabaseConfiguration(
                name = name,
                crispyFishDatabase = dir("crispy-fish"),
                snoozleDatabase = dir("snoozle"),
                default = default
        )
    }
}