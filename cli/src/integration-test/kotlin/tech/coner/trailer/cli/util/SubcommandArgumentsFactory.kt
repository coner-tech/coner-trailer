package tech.coner.trailer.cli.util

import java.nio.file.Path
import tech.coner.trailer.Club
import tech.coner.trailer.Event
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

    fun motorsportregMemberList() = SubcommandArguments(
        "motorsportreg", "member", "list"
    )

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