package org.coner.trailer

import java.math.BigDecimal

data class Class(
    val abbreviation: String,
    val name: String,
    val sort: Int?,
    val paxed: Boolean,
    val paxFactor: BigDecimal?,
    val parent: Parent?
) : Comparable<Class> {

    fun requirePaxFactor() = paxFactor ?: PaxFactors.one

    override fun compareTo(other: Class): Int {
        return compareValues(sort, other.sort)
    }

    data class Parent(val name: String)
}