package tech.coner.trailer.io

import assertk.Assert
import assertk.assertions.prop

fun Assert<DatabaseConfiguration>.name() = prop("name") { it.name }
fun Assert<DatabaseConfiguration>.crispyFishDatabase() = prop("crispyFishDatabase") { it.crispyFishDatabase }
fun Assert<DatabaseConfiguration>.snoozleDatabase() = prop("snoozleDatabase") { it.snoozleDatabase }
fun Assert<DatabaseConfiguration>.motorsportReg() = prop("motorsportReg") { it.motorsportReg }
fun Assert<DatabaseConfiguration>.default() = prop("default") { it.default }

fun Assert<DatabaseConfiguration.MotorsportReg>.username() = prop("username") { it.username }
fun Assert<DatabaseConfiguration.MotorsportReg>.organizationId() = prop("organizationId") { it.organizationId }
