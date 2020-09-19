package org.coner.trailer

object TestGroupings {

    object Lscc2019 {

        val BS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "BS",
                    name = "B Street",
                    sort = 3
            )
        val ES: Grouping
            get() = Grouping.Singular(
                    abbreviation = "ES",
                    name = "E Street",
                    sort = 6
            )
        val GS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "GS",
                    name = "G Street",
                    sort = 8
            )
        val HS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "HS",
                    name = "H Street",
                    sort = 9
            )
        val STR: Grouping
            get() = Grouping.Singular(
                    abbreviation = "STR",
                    name = "Street Touring R",
                    sort = 14
            )
        val NOV: Grouping
            get() = Grouping.Singular(
                    abbreviation = "NOV",
                    name = "Novice",
                    sort = 49
            )
    }
}