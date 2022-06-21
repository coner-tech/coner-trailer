package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.StagingRegistration
import tech.coner.crispyfish.model.StagingRun
import tech.coner.trailer.*

class CrispyFishParticipantMapper(
    private val crispyFishClassingMapper: CrispyFishClassingMapper,
) {

    fun toCore(
        allClassesByAbbreviation: Map<String, Class>,
        peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        stagingRun: StagingRun
    ): Participant? {
        val registration = stagingRun.registration
        val stagingRegistration = stagingRun.stagingRegistration
        return when {
            registration != null -> toCoreFromRegistration(
                allClassesByAbbreviation = allClassesByAbbreviation,
                peopleMap = peopleMap,
                registration = registration
            )
            stagingRegistration != null -> toCoreFromStagingRegistration(allClassesByAbbreviation, peopleMap, stagingRegistration)
            else -> null
        }
    }

    fun toCoreFromRegistration(
        allClassesByAbbreviation: Map<String, Class>,
        peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        registration: Registration
    ): Participant {
        val classing = crispyFishClassingMapper.toCore(
            allClassesByAbbreviation = allClassesByAbbreviation,
            cfRegistration = registration
        )
        val registrationNumber = registration.signage.number
        val registrationFirstName = registration.firstName
        val registrationLastName = registration.lastName
        val peopleMapKey = if (
            classing != null
            && registrationNumber != null
            && registrationFirstName != null
            && registrationLastName != null
        ) {
            Event.CrispyFishMetadata.PeopleMapKey(
                classing = classing,
                number = registrationNumber,
                firstName = registrationFirstName,
                lastName = registrationLastName
            )
        } else {
            null
        }
        val withPerson = peopleMapKey?.let { peopleMap[it] }
        return Participant(
            person = withPerson,
            firstName = registrationFirstName,
            lastName = registrationLastName,
            car = Car(
                model = registration.carModel,
                color = registration.carColor
            ),
            signage = Signage(
                classing = crispyFishClassingMapper.toCore(
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfRegistration = registration
                ),
                number = registration.signage.number,
            ),
            seasonPointsEligible = withPerson != null,
            sponsor = registration.sponsor
        )
    }

    private fun toCoreFromStagingRegistration(
        allClassesByAbbreviation: Map<String, Class>,
        peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        stagingRegistration: StagingRegistration
    ): Participant {
        val classing = crispyFishClassingMapper.toCore(
            allClassesByAbbreviation = allClassesByAbbreviation,
            cfStagingRegistration = stagingRegistration
        )
        val nameSegments = stagingRegistration.name?.split(' ')
        val stagingRegistrationNumber = stagingRegistration.signage?.number
        val stagingRegistrationFirstName = nameSegments?.firstOrNull()
        val stagingRegistrationLastName = nameSegments?.lastOrNull()
        val peopleMapKey = if (
            classing != null
            && stagingRegistrationNumber != null
            && stagingRegistrationFirstName != null
            && stagingRegistrationLastName != null
        ) {
            Event.CrispyFishMetadata.PeopleMapKey(
                classing = classing,
                number = stagingRegistrationNumber,
                firstName = stagingRegistrationFirstName,
                lastName = stagingRegistrationLastName
            )
        } else {
            null
        }
        val withPerson = peopleMapKey?.let { peopleMap[it] }
        return Participant(
            person = withPerson,
            firstName = stagingRegistrationFirstName,
            lastName = stagingRegistrationLastName,
            car = Car(
                model = stagingRegistration.car,
                color = stagingRegistration.carColor
            ),
            signage = Signage(
                classing = crispyFishClassingMapper.toCore(
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfStagingRegistration = stagingRegistration
                ),
                number = stagingRegistration.signage?.number,
            ),
            seasonPointsEligible = withPerson != null,
            sponsor = null
        )
    }
}