package org.coner.trailer.cli.util

import java.nio.file.Path

class IntegrationTestAppArgumentBuilder(
    private val configDir: Path,
    private val snoozleDir: Path,
    private val crispyFishDir: Path
) {

    fun build(vararg args: String): Array<String> {
        return mutableListOf("--config-dir", "$configDir")
            .apply { addAll(args) }
            .toTypedArray()
    }

    fun buildConfigureDatabaseAdd(databaseName: String): Array<String> {
        return build(
            "config", "database", "add",
            "--name", databaseName,
            "--crispy-fish-database", "$crispyFishDir",
            "--snoozle-database", "$snoozleDir",
            "--motorsportreg-username", "motorsportreg-username",
            "--motorsportreg-organization-id", "motorsportreg-organization-id",
            "--default",
        )
    }
}