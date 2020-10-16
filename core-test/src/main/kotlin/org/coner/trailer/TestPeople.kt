package org.coner.trailer

object TestPeople {

    // Fictional names only

    val DOMINIC_ROGERS by lazy { factory(
            firstName = "Dominic",
            lastName = "Rogers",
            memberId = "2019-00061"
    ) }
    val BRANDY_HUFF by lazy { factory(
            firstName = "Brandy",
            lastName = "Huff",
            memberId = "2019-00080"
    ) }
    val BRYANT_MORAN by lazy { factory(
            firstName ="Bryant",
            lastName = "Moran",
            memberId = "2019-00125"
    ) }
    val REBECCA_JACKSON by lazy { factory(
            firstName ="Rebecca",
            lastName = "Jackson",
            memberId = "1807"
    ) }
    val ANASTASIA_RIGLER by lazy { factory(
            firstName ="Anastasia",
            lastName = "Rigler",
            memberId = "2019-00094"
    ) }
    val JIMMY_MCKENZIE by lazy { factory(
            firstName ="Jimmy",
            lastName = "Mckenzie",
            memberId = "2476"
    ) }
    val EUGENE_DRAKE by lazy { factory(
            firstName ="Eugene",
            lastName = "Drake",
            memberId = "2019-00057"
    ) }
    val BENNETT_PANTONE by lazy { factory(
            firstName ="Bennett",
            lastName = "Pantone",
            memberId = "2019-00295"
    ) }
    val TERI_POTTER by lazy { factory(
            firstName ="Teri",
            lastName = "Potter",
            memberId = "2019-00051"
    ) }
    val HARRY_WEBSTER by lazy { factory(
            firstName ="Harry",
            lastName = "Webster",
            memberId = "2276"
    ) }
    val NORMAN_ROBINSON by lazy { factory(
            firstName ="Norman",
            lastName = "Robinson"
    ) }
    val JOHNNIE_ROWE by lazy { factory(
            firstName ="Johnnie",
            lastName = "Rowe"
    ) }

    val all: List<Person> by lazy { listOf(
            DOMINIC_ROGERS,
            BRANDY_HUFF,
            BRYANT_MORAN,
            REBECCA_JACKSON,
            ANASTASIA_RIGLER,
            JIMMY_MCKENZIE,
            EUGENE_DRAKE,
            BENNETT_PANTONE,
            TERI_POTTER,
            HARRY_WEBSTER,
            NORMAN_ROBINSON,
            JOHNNIE_ROWE
    ) }

    private fun factory(
            firstName: String,
            lastName: String,
            memberId: String? = null
    ): Person {
        fun synthesizeClubMemberId() = "$firstName-$lastName".toLowerCase()
        return Person(
                clubMemberId = memberId ?: synthesizeClubMemberId(),
                firstName = firstName,
                lastName = lastName
        )
    }
}