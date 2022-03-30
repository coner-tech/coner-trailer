package tech.coner.trailer.io

import java.nio.file.Files
import java.nio.file.Path

data class DatabaseConfiguration(
    val name: String,
    val crispyFishDatabase: Path,
    val snoozleDatabase: Path,
    val motorsportReg: MotorsportReg?,
    val default: Boolean
) {
    init {
        require(name.all { it.isLetterOrDigit() || it == '-' || it == '_' || it == ' ' }) { "name must be alphanumeric" }

        require(Files.isDirectory(crispyFishDatabase)) { "crispyFishDatabase must be a directory" }
        require(Files.isReadable(crispyFishDatabase)) { "crispyFishDatabase must be readable" }

        require(Files.isDirectory(snoozleDatabase)) { "snoozleDatabase must be a directory" }
        require(Files.isReadable(snoozleDatabase)) { "snoozleDatabase must be readable" }
        require(Files.isWritable(snoozleDatabase)) { "snoozleDatabase must be writable" }
    }

    data class MotorsportReg(
        val username: String?,
        val organizationId: String?
    )

    fun asRelativeToCrispyFishDatabase(other: Path): Path {
        return when {
            !other.isAbsolute -> other
            other.startsWith(crispyFishDatabase) -> crispyFishDatabase.relativize(other)
            else -> throw IllegalArgumentException("Other path is absolute but doesn't start with crispy fish database: $other")
        }
    }
}