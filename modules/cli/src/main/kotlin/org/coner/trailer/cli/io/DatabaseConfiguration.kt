package org.coner.trailer.cli.io

import java.io.File

data class DatabaseConfiguration(
        val name: String,
        val crispyFishDatabase: File,
        val snoozleDatabase: File
) {
    init {
        require(name.all { it.isLetterOrDigit() }) { "name must be alphanumeric" }

        require(crispyFishDatabase.exists()) { "crispyFishDatabase must exist" }
        require(crispyFishDatabase.isDirectory) { "crispyFishDatabase must be a directory" }
        require(crispyFishDatabase.canRead()) { "crispyFishDatabase must be readable" }

        require(snoozleDatabase.exists()) { "snoozleDatabase must exist" }
        require(snoozleDatabase.isDirectory) { "snoozleDatabase must be a directory" }
        require(snoozleDatabase.canRead()) { "snoozleDatabase must be readable" }
        require(snoozleDatabase.canWrite()) { "snoozleDatabase must be writable" }
    }
}