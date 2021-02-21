package org.coner.trailer

object TestGroupings {

    object Lscc2019 {

        val BS by lazy { Grouping.Singular(
            abbreviation = "BS",
            name = "B Street",
            sort = 3
        ) }
        val CS by lazy { Grouping.Singular(
            abbreviation = "CS",
            name = "C Street",
             sort = 4
        ) }
        val ES by lazy { Grouping.Singular(
            abbreviation = "ES",
            name = "E Street",
            sort = 6
        ) }
        val GS by lazy { Grouping.Singular(
            abbreviation = "GS",
            name = "G Street",
            sort = 8
        ) }
        val HS by lazy { Grouping.Singular(
            abbreviation = "HS",
            name = "H Street",
            sort = 9
        ) }
        val STR by lazy { Grouping.Singular(
            abbreviation = "STR",
            name = "Street Touring R",
            sort = 14
        ) }
        val NOV by lazy { Grouping.Singular(
            abbreviation = "NOV",
            name = "Novice",
            sort = 49
        ) }
    }
}