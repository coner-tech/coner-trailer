package org.coner.trailer

object TestParticipants {
    object Thscc2019Points1 {
        val NORMAN_ROBINSON: Participant
            get() = Participant(
                    person = TestPeople.NORMAN_ROBINSON,
                    grouping = TestGroupings.THSCC_2019_GS,
                    car = Car(
                            model = "2017 Volkswagen GTI",
                            color = "White"
                    )
            )
        val TERI_POTTER: Participant
            get() = Participant(
                    person = TestPeople.TERI_POTTER,
                    grouping = TestGroupings.THSCC_2019_GS,
                    car = Car(
                            model = "2017 Ford Focus ST",
                            color = "White"
                    )
            )
        val EUGENE_DRAKE: Participant
            get() = Participant(
                    person = TestPeople.EUGENE_DRAKE,
                    grouping = TestGroupings.THSCC_2019_STR,
                    car = Car(
                            model = "1999 Mazda Miata",
                            color = "" // empty
                    )
            )
        val JIMMY_MCKENZIE: Participant
            get() = Participant(
                    person = TestPeople.JIMMY_MCKENZIE,
                    grouping = TestGroupings.THSCC_2019_STR,
                    car = Car(
                            model = "1994 Mazda Miata",
                            color = "White"
                    )
            )
        val REBECCA_JACKSON: Participant
            get() = Participant(
                    person = TestPeople.REBECCA_JACKSON,
                    grouping = TestGroupings.THSCC_2019_HS,
                    car = Car(
                            model = "2017 Mazda 6",
                            color = "Red"
                    )
            )
        val BRANDY_HUFF: Participant
            get() = Participant(
                    person = TestPeople.BRANDY_HUFF,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.THSCC_2019_NOV to TestGroupings.THSCC_2019_BS
                    ),
                    car = Car(
                            model = "2018 Subaru WRX",
                            color = "WorldRallyBlue"
                    )
            )
        val BRYANT_MORAN: Participant
            get() = Participant(
                    person = TestPeople.BRYANT_MORAN,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.THSCC_2019_NOV to TestGroupings.THSCC_2019_GS
                    ),
                    car = Car(
                            model = "2017 Volkswagen GTI",
                            color = "White"
                    )
            )
        val DOMINIC_ROGERS: Participant
            get() = Participant(
                    person = TestPeople.DOMINIC_ROGERS,
                    grouping = Grouping.Paired(
                            pair = TestGroupings.THSCC_2019_NOV to TestGroupings.THSCC_2019_ES
                    ),
                    car = Car(
                            model = "2002 Mazda Miata",
                            color = "Blue"
                    )
            )
    }



}