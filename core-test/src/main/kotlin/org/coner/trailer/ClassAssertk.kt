package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop


fun Assert<Class>.name() = prop("name") { it.name }
fun Assert<Class>.hasName(expected: String) = name().isEqualTo(expected)

fun Assert<Class>.abbreviation() = prop("abbreviation") { it.abbreviation }
fun Assert<Class>.hasAbbreviation(expected: String) = abbreviation().isEqualTo(expected)

fun Assert<Class>.sort() = prop("sort") { it.sort }
fun Assert<Class>.hasSort(expected: Int) = sort().isEqualTo(expected)

fun Assert<Class>.paxed() = prop("paxed") { it.paxed }
fun Assert<Class>.paxFactor() = prop("paxFactor") { it.paxFactor }

fun Assert<Class>.parent() = prop("parent") { it.parent }
@JvmName("classParentName")
fun Assert<Class.Parent>.name() = prop("name") { it.name }
