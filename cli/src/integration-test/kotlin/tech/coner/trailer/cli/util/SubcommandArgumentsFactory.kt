package tech.coner.trailer.cli.util

import java.nio.file.Path
import tech.coner.trailer.Club
import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Person
import tech.coner.trailer.Policy

class SubcommandArgumentsFactory(
    private val snoozleDir: Path,
    private val crispyFishDir: Path
) {

    fun clubSet(club: Club) = SubcommandArguments(
            "club", "set",
            "--name", club.name
        )

    fun configDatabaseAdd(
        databaseName: String,
    ) = SubcommandArguments(
        "config", "database", "add",
        "--name", databaseName,
        "--crispy-fish-database", "$crispyFishDir",
        "--snoozle-database", "$snoozleDir",
        "--default",
    )

    fun configureDatabaseSnoozleInitialize() = SubcommandArguments(
            "config", "database", "snoozle", "initialize"
    )

    fun eventAddCrispyFish(
        event: Event,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path
    ) = SubcommandArguments(
        "event", "add",
        "--id", "${event.id}",
        "--name", event.name,
        "--date", "${event.date}",
        "--crispy-fish-event-control-file", "$crispyFishEventControlFile",
        "--crispy-fish-class-definition-file", "$crispyFishClassDefinitionFile",
        "--policy-id", "${event.policy.id}"
    )

    fun eventCheck(event: Event) = SubcommandArguments(
        "event", "check", "${event.id}"
    )

    fun eventCrispyFishPersonMapAdd(event: Event, participant: Participant) = SubcommandArguments {
        addAll(listOf(
            "event", "crispy-fish", "person-map-add",
            "--handicap", participant.signage?.classing?.handicap?.abbreviation!!,
            "--number", participant.signage?.number!!,
            "--first-name", participant.firstName!!,
            "--last-name", participant.lastName!!,
            "--person-id", "${participant.person!!.id}"
        ))
        participant.signage?.classing?.group?.abbreviation?.also {
            addAll(listOf("--group", it))
        }
        add("${event.id}")
    }

    fun eventParticipantList(event: Event) = SubcommandArguments(
        "event", "participant", "list", "${event.id}"
    )

    fun eventRunList(event: Event) = SubcommandArguments(
        "event", "run", "list", "${event.id}"
    )

    fun motorsportregMemberList() = SubcommandArguments(
        "motorsportreg", "member", "list"
    )

    fun personAdd(
        person: Person
    ) = SubcommandArguments {
        addAll(listOf(
            "person", "add",
            "--id", "${person.id}",
            "--first-name", person.firstName,
            "--last-name", person.lastName
        ))
        person.clubMemberId?.also {
            addAll(listOf("--club-member-id", it))
        }
        person.motorsportReg?.memberId?.also {
            addAll(listOf("--motorsportreg-member-id", it))
        }
    }

    fun policyAdd(
        policy: Policy
    ) = SubcommandArguments(
        "policy", "add",
        "--id", "${policy.id}",
        "--name", policy.name,
        "--cone-penalty-seconds", "${policy.conePenaltySeconds}",
        "--pax-time-style", policy.paxTimeStyle.name.toLowerCase(),
        "--final-score-style", policy.finalScoreStyle.name.toLowerCase()
    )

}