package tech.coner.trailer

object TestPeople {

    // Fictional names only

    val DOMINIC_ROGERS by lazy { factory(
            firstName = "Dominic",
            lastName = "Rogers",
            memberId = "2019-00061",
            motorsportRegMemberId = "3E0D132D-F8A7-707D-F404A52E2F811210"
    ) }
    val BRANDY_HUFF by lazy { factory(
            firstName = "Brandy",
            lastName = "Huff",
            memberId = "2019-00080",
            motorsportRegMemberId = "A206D935-FCA8-E9C3-308035B0C3A16FBB"
    ) }
    val BRYANT_MORAN by lazy { factory(
            firstName ="Bryant",
            lastName = "Moran",
            memberId = "2019-00125",
            motorsportRegMemberId = "B1D48A87-F3A6-AF7D-7B2A62DF91AB294A"
    ) }
    val REBECCA_JACKSON by lazy { factory(
            firstName ="Rebecca",
            lastName = "Jackson",
            memberId = "1807",
            motorsportRegMemberId = "7405668C-C9BC-F69E-6AC8076A09E3A854"
    ) }
    val ANASTASIA_RIGLER by lazy { factory(
            firstName ="Anastasia",
            lastName = "Rigler",
            memberId = "2019-00094",
            motorsportRegMemberId = "84DC50DB-D282-A70D-B63DE3842DE98D4A"
    ) }
    val JIMMY_MCKENZIE by lazy { factory(
            firstName ="Jimmy",
            lastName = "Mckenzie",
            memberId = "2476",
            motorsportRegMemberId = "68D25DAD-060C-A0BB-E9C2B0CE56286B50"
    ) }
    val EUGENE_DRAKE by lazy { factory(
            firstName ="Eugene",
            lastName = "Drake",
            memberId = "2019-00057",
            motorsportRegMemberId = "2399A0B7-A20E-A1A4-D81FA58E1B34D7CC"
    ) }
    val BENNETT_PANTONE by lazy { factory(
            firstName ="Bennett",
            lastName = "Pantone",
            memberId = "2019-00295",
            motorsportRegMemberId = "45A62559-CF16-4A55-2D59D7262D613CEA"
    ) }
    val TERI_POTTER by lazy { factory(
            firstName ="Teri",
            lastName = "Potter",
            memberId = "2019-00051",
            motorsportRegMemberId = "0643F8B5-C8AC-BB6F-01DFA489BF66DC67"
    ) }
    val HARRY_WEBSTER by lazy { factory(
            firstName ="Harry",
            lastName = "Webster",
            memberId = "2276",
            motorsportRegMemberId = "A754F051-AC42-A66B-DA00B038767A5ED1"
    ) }
    val NORMAN_ROBINSON by lazy { factory(
            firstName ="Norman",
            lastName = "Robinson",
            motorsportRegMemberId = "EDAA4F30-0204-6C66-7C809ED4855EC805"
    ) }
    val JOHNNIE_ROWE by lazy { factory(
            firstName ="Johnnie",
            lastName = "Rowe"
    ) }
    val CLAIRE_DICKERSON by lazy { factory(
        firstName = "Claire",
        lastName = "Dickerson"
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
            memberId: String? = null,
            motorsportRegMemberId: String? = null
    ): Person {
        fun synthesizeClubMemberId() = "$firstName-$lastName".toLowerCase()
        return Person(
                clubMemberId = memberId ?: synthesizeClubMemberId(),
                firstName = firstName,
                lastName = lastName,
                motorsportReg = motorsportRegMemberId?.let { Person.MotorsportRegMetadata(
                        memberId = it
                ) }
        )
    }
}