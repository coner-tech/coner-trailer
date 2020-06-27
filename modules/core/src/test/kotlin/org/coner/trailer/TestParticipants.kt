package org.coner.trailer

object TestParticipants {
    object Lscc2019Points1 {
        val NORMAN_ROBINSON: Participant by lazy {
            factory(
                    person = TestPeople.NORMAN_ROBINSON,
                    grouping = TestGroupings.Lscc2019.GS,
                    car = Car(
                            model = "2017 Volkswagen GTI",
                            color = "White"
                    )
            )
        }
        val TERI_POTTER: Participant by lazy {
            factory(
                    person = TestPeople.TERI_POTTER,
                    grouping = TestGroupings.Lscc2019.GS,
                    car = Car(
                            model = "2017 Ford Focus ST",
                            color = "White"
                    )
            )
        }
        val EUGENE_DRAKE: Participant by lazy {
            factory(
                    person = TestPeople.EUGENE_DRAKE,
                    grouping = TestGroupings.Lscc2019.GS,
                    car = Car(
                            model = "1999 Mazda Miata",
                            color = "" // empty
                    )
            )
        }
        val JIMMY_MCKENZIE: Participant by lazy {
            factory(
                    person = TestPeople.JIMMY_MCKENZIE,
                    grouping = TestGroupings.Lscc2019.STR,
                    car = Car(
                            model = "1994 Mazda Miata",
                            color = "White"
                    )
            )
        }
        val REBECCA_JACKSON: Participant by lazy {
            factory(
                    person = TestPeople.REBECCA_JACKSON,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "2017 Mazda 6",
                            color = "Red"
                    )
            )
        }
        val BRANDY_HUFF: Participant by lazy {
            factory(
                    person = TestPeople.BRANDY_HUFF,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.Lscc2019.NOV to TestGroupings.Lscc2019.BS
                    ),
                    car = Car(
                            model = "2018 Subaru WRX",
                            color = "WorldRallyBlue"
                    )
            )
        }
        val BRYANT_MORAN: Participant by lazy {
            factory(
                    person = TestPeople.BRYANT_MORAN,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.Lscc2019.NOV to TestGroupings.Lscc2019.GS
                    ),
                    car = Car(
                            model = "2017 Volkswagen GTI",
                            color = "White"
                    )
            )
        }
        val DOMINIC_ROGERS: Participant by lazy {
            factory(
                    person = TestPeople.DOMINIC_ROGERS,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.Lscc2019.NOV to TestGroupings.Lscc2019.ES
                    ),
                    car = Car(
                            model = "2002 Mazda Miata",
                            color = "Blue"
                    )
            )
        }
    }

    object LsccTieBreaking {
        val REBECCA_JACKSON: Participant by lazy {
            factory(
                    person = TestPeople.REBECCA_JACKSON,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "2017 Mazda 6",
                            color = "Red"
                    )
            )
        }
        val JIMMY_MCKENZIE: Participant by lazy {
            factory(
                    person = TestPeople.JIMMY_MCKENZIE,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "1994 Mazda Miata",
                            color = "White"
                    )
            )
        }
        val EUGENE_DRAKE: Participant by lazy {
            factory(
                    person = TestPeople.EUGENE_DRAKE,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "1999 Mazda Miata",
                            color = "" // empty
                    )
            )
        }
        val TERI_POTTER: Participant by lazy {
            factory(
                    person = TestPeople.TERI_POTTER,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "2017 Ford Focus ST",
                            color = "White"
                    )
            )
        }
        val HARRY_WEBSTER: Participant by lazy {
            factory(
                    person = TestPeople.HARRY_WEBSTER,
                    grouping = TestGroupings.Lscc2019.HS,
                    car = Car(
                            model = "2013 Mazda mazdaspeed3",
                            color = "silver"
                    )
            )
        }
    }
    
    private fun factory(
            person: Person,
            grouping: Grouping,
            car: Car,
            seasonPointsEligible: Boolean = true
    ) = Participant(
            person = person,
            grouping = grouping,
            car = car,
            seasonPointsEligible = seasonPointsEligible
    )

}