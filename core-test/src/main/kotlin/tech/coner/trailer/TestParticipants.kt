package tech.coner.trailer

object TestParticipants {
    object Lscc2019Points1 {
        val NORMAN_ROBINSON: Participant by lazy {
            factory(
                person = TestPeople.NORMAN_ROBINSON,
                group = null,
                handicap = TestClasses.Lscc2019.GS,
                number = "52",
                car = Car(
                    model = "2017 Volkswagen GTI",
                    color = "White"
                )
            )
        }
        val TERI_POTTER: Participant by lazy {
            factory(
                person = TestPeople.TERI_POTTER,
                group = null,
                handicap = TestClasses.Lscc2019.GS,
                number = "40",
                car = Car(
                    model = "2017 Ford Focus ST",
                    color = "White"
                )
            )
        }
        val EUGENE_DRAKE: Participant by lazy {
            factory(
                person = TestPeople.EUGENE_DRAKE,
                group = null,
                handicap = TestClasses.Lscc2019.GS,
                number = "1",
                car = Car(
                    model = "1999 Mazda Miata",
                    color = null
                )
            )
        }
        val JIMMY_MCKENZIE: Participant by lazy {
            factory(
                person = TestPeople.JIMMY_MCKENZIE,
                group = null,
                handicap = TestClasses.Lscc2019.STR,
                number = "23",
                car = Car(
                    model = "1994 Mazda Miata",
                    color = "White"
                )
            )
        }
        val REBECCA_JACKSON: Participant by lazy {
            factory(
                person = TestPeople.REBECCA_JACKSON,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "1",
                car = Car(
                    model = "2017 Mazda 6",
                    color = "Red"
                )
            )
        }
        val BRANDY_HUFF: Participant by lazy {
            factory(
                person = TestPeople.BRANDY_HUFF,
                group = TestClasses.Lscc2019.NOV,
                handicap = TestClasses.Lscc2019.BS,
                number = "177",
                car = Car(
                    model = "2018 Subaru WRX",
                    color = "WorldRallyBlue"
                )
            )
        }
        val BRYANT_MORAN: Participant by lazy {
            factory(
                person = TestPeople.BRYANT_MORAN,
                group = TestClasses.Lscc2019.NOV,
                handicap = TestClasses.Lscc2019.GS,
                number = "58",
                car = Car(
                    model = "2017 Volkswagen GTI",
                    color = "White"
                )
            )
        }
        val DOMINIC_ROGERS: Participant by lazy {
            factory(
                person = TestPeople.DOMINIC_ROGERS,
                group = TestClasses.Lscc2019.NOV,
                handicap = TestClasses.Lscc2019.ES,
                number = "18",
                car = Car(
                    model = "2002 Mazda Miata",
                    color = "Blue"
                )
            )
        }
        val all: List<Participant> by lazy {
            listOf(
                NORMAN_ROBINSON,
                TERI_POTTER,
                EUGENE_DRAKE,
                JIMMY_MCKENZIE,
                REBECCA_JACKSON,
                BRANDY_HUFF,
                BRYANT_MORAN,
                DOMINIC_ROGERS
            )
        }
    }

    object Lscc2019Points1Simplified {
        val ANASTASIA_RIGLER: Participant by lazy { factory(
            person = TestPeople.ANASTASIA_RIGLER,
            group = null,
            handicap = TestClasses.Lscc2019.HS,
            number = "130",
            car = Car(model = "2015 Honda CR-Z", color = "White")
        ) }
        val REBECCA_JACKSON: Participant by lazy { factory(
            person = TestPeople.REBECCA_JACKSON,
            group = null,
            handicap = TestClasses.Lscc2019.HS,
            number = "1",
            car = Car(model = "2017 Mazda 6", color = "Red")
        ) }
        val EUGENE_DRAKE: Participant by lazy { factory(
            person = TestPeople.EUGENE_DRAKE,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "1",
            car = Car(model = "1999 Mazda Miata", color = null)
        ) }
        val JIMMY_MCKENZIE: Participant by lazy { factory(
            person = TestPeople.JIMMY_MCKENZIE,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "23",
            car = Car(model = "1994 Mazda Miata", color = "White")
        ) }
        val BRANDY_HUFF: Participant by lazy { factory(
            person = TestPeople.BRANDY_HUFF,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.BS,
            number = "177",
            car = Car(model = "2018 Subaru WRX", color = "WorldRallyBlue")
        ) }
        val BRYANT_MORAN: Participant by lazy { factory(
            person = TestPeople.BRYANT_MORAN,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.ES,
            number = "58",
            car = Car(model = "2017 Volkswagen GTI", color = "White")
        ) }
        val DOMINIC_ROGERS: Participant by lazy { factory(
            person = TestPeople.DOMINIC_ROGERS,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.ES,
            number = "18",
            car = Car(model = "2002 Mazda Miata", color = "Blue")
        ) }
        val all: List<Participant> by lazy {
            listOf(
                ANASTASIA_RIGLER,
                REBECCA_JACKSON,
                EUGENE_DRAKE,
                JIMMY_MCKENZIE,
                BRANDY_HUFF,
                BRYANT_MORAN,
                DOMINIC_ROGERS
            )
        }
    }

    object Lscc2019Points2Simplified {
        val ANASTASIA_RIGLER: Participant by lazy { factory(
            person = TestPeople.ANASTASIA_RIGLER,
            group = null,
            handicap = TestClasses.Lscc2019.HS,
            number = "130",
            car = Car(model = "2015 Honda CR-Z", color = "White")
        ) }
        val REBECCA_JACKSON: Participant by lazy { factory(
            person = TestPeople.REBECCA_JACKSON,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "8",
            car = Car(model = "2002 Honda S2000", color = "Silver")
        ) }
        val JIMMY_MCKENZIE: Participant by lazy { factory(
            person = TestPeople.JIMMY_MCKENZIE,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "23",
            car = Car(model = "1994 Mazda Miata", color = "White")
        ) }
        val BRANDY_HUFF: Participant by lazy { factory(
            person = TestPeople.BRANDY_HUFF,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.BS,
            number = "52",
            car = Car(model = "2018 Subaru WRX", color = "WorldRallyBlue")
        ) }
        val DOMINIC_ROGERS: Participant by lazy { factory(
            person = TestPeople.DOMINIC_ROGERS,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.ES,
            number = "18",
            car = Car(model = "2002 Mazda Miata", color = "Blue"),
        ) }
        val BENNETT_PANTONE: Participant by lazy { factory(
            person = TestPeople.BENNETT_PANTONE,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.CS,
            number = "20",
            car = Car(model = "2003 Honda S2000", color = "Spa Yellow")
        ) }
        val all: List<Participant> by lazy {
            listOf(
                ANASTASIA_RIGLER,
                REBECCA_JACKSON,
                JIMMY_MCKENZIE,
                BRANDY_HUFF,
                DOMINIC_ROGERS,
                BENNETT_PANTONE
            )
        }
    }

    object Lscc2019Points3Simplified {
        val ANASTASIA_RIGLER: Participant by lazy { factory(
            person = TestPeople.ANASTASIA_RIGLER,
            group = null,
            handicap = TestClasses.Lscc2019.HS,
            number = "130",
            car = Car(model = "2015 Honda CR-Z", "White")
        ) }
        val REBECCA_JACKSON: Participant by lazy { factory(
            person = TestPeople.REBECCA_JACKSON,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "8",
            car = Car(model = "2002 Honda S2000", color = "Silver")
        ) }
        val JIMMY_MCKENZIE: Participant by lazy { factory(
            person = TestPeople.JIMMY_MCKENZIE,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "23",
            car = Car(model = "1994 Mazda Miata", color = "White")
        ) }
        val EUGENE_DRAKE: Participant by lazy { factory(
            person = TestPeople.EUGENE_DRAKE,
            group = null,
            handicap = TestClasses.Lscc2019.STR,
            number = "1",
            car = Car(model = "1999 Mazda Miata", color = null)
        ) }
        val BRANDY_HUFF: Participant by lazy { factory(
            person = TestPeople.BRANDY_HUFF,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.BS,
            number = "52",
            car = Car(model = "2018 Subaru WRX", color = "WorldRallyBlue")
        ) }
        val BRYANT_MORAN: Participant by lazy { factory(
            person = TestPeople.BRYANT_MORAN,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.GS,
            number = "58",
            car = Car(model = "2017 Volkswagen GTI", color = "White")
        ) }
        val DOMINIC_ROGERS: Participant by lazy { factory(
            person = TestPeople.DOMINIC_ROGERS,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.ES,
            number = "18",
            car = Car(model = "2002 Mazda Miata", color = "Blue")
        ) }
        val BENNETT_PANTONE: Participant by lazy { factory(
            person = TestPeople.BENNETT_PANTONE,
            group = TestClasses.Lscc2019.NOV,
            handicap = TestClasses.Lscc2019.CS,
            number = "20",
            car = Car(model = "2003 Honda S2000", color = "Spa Yellow")
        ) }
    }

    object LsccTieBreaking {
        val REBECCA_JACKSON: Participant by lazy {
            factory(
                person = TestPeople.REBECCA_JACKSON,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "1",
                car = Car(
                    model = "2017 Mazda 6",
                    color = "Red"
                )
            )
        }
        val JIMMY_MCKENZIE: Participant by lazy {
            factory(
                person = TestPeople.JIMMY_MCKENZIE,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "23",
                car = Car(
                    model = "1994 Mazda Miata",
                    color = "White"
                )
            )
        }
        val EUGENE_DRAKE: Participant by lazy {
            factory(
                person = TestPeople.EUGENE_DRAKE,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "1",
                car = Car(
                    model = "1999 Mazda Miata",
                    color = null
                )
            )
        }
        val TERI_POTTER: Participant by lazy {
            factory(
                person = TestPeople.TERI_POTTER,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "40",
                car = Car(
                    model = "2017 Ford Focus ST",
                    color = "White"
                )
            )
        }
        val HARRY_WEBSTER: Participant by lazy {
            factory(
                person = TestPeople.HARRY_WEBSTER,
                group = null,
                handicap = TestClasses.Lscc2019.HS,
                number = "46",
                car = Car(
                    model = "2013 Mazda mazdaspeed3",
                    color = "silver"
                )
            )
        }
        val all: List<Participant> by lazy {
            listOf(
                REBECCA_JACKSON,
                JIMMY_MCKENZIE,
                EUGENE_DRAKE,
                TERI_POTTER,
                HARRY_WEBSTER
            )
        }
    }

    private fun factory(
        person: Person,
        group: Class?,
        handicap: Class,
        number: String,
        car: Car,
        seasonPointsEligible: Boolean = true
    ) = Participant(
        person = person,
        firstName = person.firstName,
        lastName = person.lastName,
        signage = Signage(
            classing = Classing(group = group, handicap = handicap),
            number = number
        ),
        car = car,
        seasonPointsEligible = seasonPointsEligible,
        sponsor = null
    )

}