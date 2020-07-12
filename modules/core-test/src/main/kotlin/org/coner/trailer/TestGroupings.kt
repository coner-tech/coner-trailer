package org.coner.trailer

object TestGroupings {

    object Lscc2019 {

        val BS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "BS",
                    name = "B Street"
            )
        val ES: Grouping
            get() = Grouping.Singular(
                    abbreviation = "ES",
                    name = "E Street"
            )
        val GS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "GS",
                    name = "G Street"
            )
        val HS: Grouping
            get() = Grouping.Singular(
                    abbreviation = "HS",
                    name = "H Street"
            )
        val STR: Grouping
            get() = Grouping.Singular(
                    abbreviation = "STR",
                    name = "Street Touring R"
            )
        val NOV: Grouping
            get() = Grouping.Singular(
                    abbreviation = "NOV",
                    name = "Novice"
            )
    }
}