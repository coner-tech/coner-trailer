package tech.coner.trailer.io.payload

import java.nio.file.Path

data class ConfigAddDatabaseParam(
    val name: String,
    val crispyFishDatabase: Path,
    val snoozleDatabase: Path,
    val motorsportReg: MotorsportReg?,
    val default: Boolean
) {
    data class MotorsportReg(
        val username: String?,
        val organizationId: String?
    )
}
