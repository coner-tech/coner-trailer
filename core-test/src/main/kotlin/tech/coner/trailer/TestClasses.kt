package tech.coner.trailer

import java.math.BigDecimal

object TestClasses {

    object Lscc2019 {

        val BS by lazy { Class(
            abbreviation = "BS",
            name = "B Street",
            sort = 3,
            paxed = false,
            paxFactor = BigDecimal("0.810"),
            parent = STREET
        ) }
        val CS by lazy { Class(
            abbreviation = "CS",
            name = "C Street",
            sort = 4,
            paxed = false,
            paxFactor = BigDecimal("0.809"),
            parent = STREET
        ) }
        val ES by lazy { Class(
            abbreviation = "ES",
            name = "E Street",
            sort = 6,
            paxed = false,
            paxFactor = BigDecimal("0.789"),
            parent = STREET
        ) }
        val GS by lazy { Class(
            abbreviation = "GS",
            name = "G Street",
            sort = 8,
            paxed = false,
            paxFactor = BigDecimal("0.788"),
            parent = STREET
        ) }
        val HS by lazy { Class(
            abbreviation = "HS",
            name = "H Street",
            sort = 9,
            paxed = false,
            paxFactor = BigDecimal("0.780"),
            parent = STREET
        ) }
        val STR by lazy { Class(
            abbreviation = "STR",
            name = "Street Touring R",
            sort = 14,
            paxed = false,
            paxFactor = BigDecimal("0.827"),
            parent = STREET_TOURING
        ) }
        val NOV by lazy { Class(
            abbreviation = "NOV",
            name = "Novice",
            sort = 49,
            paxed = true,
            paxFactor = null,
            parent = NOVICE
        ) }

        val STREET by lazy { Class.Parent(
            name = "Street",
            sort = 0
        ) }
        val STREET_TOURING by lazy { Class.Parent(
            name = "Touring",
            sort = 1
        ) }
        val NOVICE by lazy { Class.Parent(
            name = "Novice",
            sort = 2
        ) }

        val all: List<Class> by lazy { listOf(
            BS,
            CS,
            ES,
            GS,
            HS,
            STR,
            NOV
        ) }

        val allByAbbreviation by lazy { all.associateBy { it.abbreviation } }
    }

    object Lscc2022 {

        val STU by lazy {
            Class(
                abbreviation = "STU",
                name = "Street Touring Ultra",
                sort = 17,
                paxed = false,
                paxFactor = BigDecimal("0.829"),
                parent = STREET_TOURING
            )
        }
        val NOV by lazy {
            Class(
                abbreviation = "NOV",
                name = "Novice",
                sort = 51,
                paxed = true,
                paxFactor = BigDecimal("1.000"),
                parent = NOVICE
            )
        }

        val STREET_TOURING by lazy {
            Class.Parent(
                name = "Street Touring",
                sort = 1
            )
        }
        val NOVICE by lazy {
            Class.Parent(
                name = "Novice",
                sort = 2
            )
        }
    }
}