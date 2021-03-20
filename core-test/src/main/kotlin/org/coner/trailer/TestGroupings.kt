package org.coner.trailer

import java.math.BigDecimal

object TestGroupings {

    object Lscc2019 {

        val BS by lazy { Grouping.Singular(
            abbreviation = "BS",
            name = "B Street",
            sort = 3,
            paxed = false,
            paxFactor = BigDecimal("0.810")
        ) }
        val CS by lazy { Grouping.Singular(
            abbreviation = "CS",
            name = "C Street",
            sort = 4,
            paxed = false,
            paxFactor = BigDecimal("0.809")
        ) }
        val ES by lazy { Grouping.Singular(
            abbreviation = "ES",
            name = "E Street",
            sort = 6,
            paxed = false,
            paxFactor = BigDecimal("0.789")
        ) }
        val GS by lazy { Grouping.Singular(
            abbreviation = "GS",
            name = "G Street",
            sort = 8,
            paxed = false,
            paxFactor = BigDecimal("0.788")
        ) }
        val HS by lazy { Grouping.Singular(
            abbreviation = "HS",
            name = "H Street",
            sort = 9,
            paxed = false,
            paxFactor = BigDecimal("0.780")
        ) }
        val STR by lazy { Grouping.Singular(
            abbreviation = "STR",
            name = "Street Touring R",
            sort = 14,
            paxed = false,
            paxFactor = BigDecimal("0.827")
        ) }
        val NOV by lazy { Grouping.Singular(
            abbreviation = "NOV",
            name = "Novice",
            sort = 49,
            paxed = true,
            paxFactor = null
        ) }
    }
}