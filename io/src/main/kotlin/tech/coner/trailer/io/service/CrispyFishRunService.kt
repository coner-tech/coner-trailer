package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Signage
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper

class CrispyFishRunService(
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val crispyFishClassService: CrispyFishClassService,
    private val crispyFishClassingMapper: CrispyFishClassingMapper,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishRunMapper: CrispyFishRunMapper
) {
    fun list(event: Event): Result<List<Run>> = try {
        val eventCrispyFish = event.requireCrispyFish()
        val context = crispyFishEventMappingContextService.load(eventCrispyFish)
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(eventCrispyFish.classDefinitionFile)
        val runs = context.allRuns
            .mapIndexed { index, (registration, run) ->
                val participant = registration
                    ?.let {
                        val classing = crispyFishClassingMapper.toCore(
                            allClassesByAbbreviation = allClassesByAbbreviation,
                            cfRegistration = registration
                        )
                        val registrationNumber = registration.number
                        val registrationFirstName = registration.firstName
                        val registrationLastName = registration.lastName
                        val peopleMapKey = if (classing != null && registrationNumber != null && registrationFirstName != null && registrationLastName != null) {
                            Event.CrispyFishMetadata.PeopleMapKey(
                                classing = classing,
                                number = registrationNumber,
                                firstName = registrationFirstName,
                                lastName = registrationLastName
                            )
                        } else {
                            null
                        }
                        peopleMapKey?.let {
                            crispyFishParticipantMapper.toCore(
                                allClassesByAbbreviation = allClassesByAbbreviation,
                                fromRegistration = registration,
                                withPerson = eventCrispyFish.peopleMap[it]
                            )
                        }
                    }
                    ?: context.staging[index]
                        ?.let {
                            Participant(
                                signage = Signage(
                                    classing = it.stagingLineRegistration?.
                                )
                            )
                        }
                crispyFishRunMapper.toCore(
                    cfRun = run ?: createEmptyRun(index),
                    cfRunIndex = index,
                    participant = participant
                )
            }
        Result.success(runs)
    } catch (t: Throwable) {
        Result.failure(t)
    }

    private fun createEmptyRun(index: Int) = tech.coner.crispyfish.model.Run(
        number = index + 1,
        rawTime = null,
        paxTime = null,
        penaltyType = null,
        cones = null,
        timeScored = null,
        timeScratchAsString = null,
        timeScratchAsDuration = null
    )
}