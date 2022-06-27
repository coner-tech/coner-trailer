package tech.coner.trailer.io.payload

import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.Run

class EventHealthCheckOutcome(
    val unmappable: List<Registration>,
    val unmappedMotorsportRegPersonMatches: List<Pair<Registration, Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>>>,
    val unmappedClubMemberIdNullRegistrations: List<Registration>,
    val unmappedClubMemberIdNotFoundRegistrations: List<Registration>,
    val unmappedClubMemberIdAmbiguousRegistrations: List<Registration>,
    val unmappedClubMemberIdMatchButNameMismatchRegistrations: List<Registration>,
    val unmappedExactMatchRegistrations: List<Registration>,
    val unusedPeopleMapKeys: List<Event.CrispyFishMetadata.PeopleMapKey>,
    val runsWithInvalidSignage: List<Run>
)