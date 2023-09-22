package tech.coner.trailer.cli.command

/**
 * Indicates that no database was chosen and/or no default was configured, and the invoked subcommand requires one
 */
class NoDatabaseChosenException : RuntimeException() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
