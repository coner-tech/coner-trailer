package tech.coner.trailer.cli.util

import tech.coner.trailer.*
import tech.coner.trailer.di.Format
import tech.coner.trailer.eventresults.EventResultsType
import java.nio.file.Path
import java.time.LocalDate
import java.util.*


class IntegrationTestAppArgumentBuilder(
    private val configDir: Path,
    private val snoozleDir: Path,
    private val crispyFishDir: Path
) {

    fun build(vararg args: String): Array<String> {
        return mutableListOf("--config-dir", "$configDir")
            .apply { addAll(args) }
            .toTypedArray()
    }

    fun configDatabaseAdd(databaseName: String): Array<String> {
        return build(
            "config", "database", "add",
            "--name", databaseName,
            "--crispy-fish-database", "$crispyFishDir",
            "--snoozle-database", "$snoozleDir",
            "--default",
        )
    }

    fun configureDatabaseSnoozleInitialize(): Array<String> {
        return build(
            "config", "database", "snoozle", "initialize"
        )
    }

    fun clubSet(club: Club): Array<String> {
        return build(
            "club", "set",
            "--name", club.name
        )
    }

    fun policyAdd(
        policy: Policy
    ): Array<String> {
        return build(
            "policy", "add",
            "--id", "${policy.id}",
            "--name", policy.name,
            "--cone-penalty-seconds", "${policy.conePenaltySeconds}",
            "--pax-time-style", policy.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", policy.finalScoreStyle.name.toLowerCase()
        )
    }

    fun eventAddCrispyFish(
        event: Event,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path
    ): Array<String> {
        return build(
            "event", "add",
            "--id", "${event.id}",
            "--name", event.name,
            "--date", "${event.date}",
            "--crispy-fish-event-control-file", "$crispyFishEventControlFile",
            "--crispy-fish-class-definition-file", "$crispyFishClassDefinitionFile",
            "--policy-id", "${event.policy.id}"
        )
    }

    fun eventCheck(
        event: Event
    ): Array<String> {
        return build(
            "event", "check",
            "${event.id}"
        )
    }

    fun eventSet(
        eventId: UUID,
        name: String? = null,
        date: LocalDate? = null,
        lifecycle: Event.Lifecycle? = null,
        setCrispyFishMetadata: Event.CrispyFishMetadata? = null,
        unsetCrispyFishMetadata: Unit? = null,
    ): Array<String> {
        return buildList {
            addAll(listOf("event", "set"))
            name?.also { addAll(listOf("--name", it)) }
            date?.also { addAll(listOf("--date", "$it")) }
            lifecycle?.also { addAll(listOf("--lifecycle", lifecycle.name)) }
            setCrispyFishMetadata?.also {
                addAll(listOf(
                    "--crispy-fish", "set",
                    "--class-definition-file", "${it.classDefinitionFile}",
                    "--event-control-file", "${it.eventControlFile}"
                ))
            }
            unsetCrispyFishMetadata?.also { addAll(listOf("--crispy-fish", "unset")) }
            add("$eventId")
        }
            .let { build(*it.toTypedArray()) }
    }

    fun personAdd(person: Person): Array<String> {
        val argumentBuilder = mutableListOf<String>().apply {
            addAll(arrayOf(
                "person", "add",
                "--id", "${person.id}"
            ))
            person.clubMemberId?.also {
                addAll(arrayOf("--club-member-id", it))
            }
            addAll(arrayOf(
                "--first-name", person.firstName,
                "--last-name", person.lastName
            ))
            person.motorsportReg?.memberId?.also {
                addAll(arrayOf("--motorsportreg-member-id", it))
            }
        }
        return build(*argumentBuilder.toTypedArray())
    }

    fun eventCrispyFishPersonMapAdd(
        event: Event,
        participant: Participant
    ): Array<String> {
        val argumentBuilder = mutableListOf<String>().apply {
            addAll(arrayOf(
                "event", "crispy-fish-person-map-add",
                "${event.id}"
            ))
            participant.signage?.classing?.group?.abbreviation?.also {
                addAll(arrayOf("--group", it))
            }
            addAll(arrayOf(
                "--handicap", participant.signage?.classing?.handicap?.abbreviation!!,
                "--number", participant.signage?.number!!,
                "--first-name", participant.firstName!!,
                "--last-name", participant.lastName!!,
                "--person-id", "${participant.person!!.id}"
            ))
        }
        return build(*argumentBuilder.toTypedArray())
    }

    fun eventResults(
        event: Event,
        type: EventResultsType,
        format: Format? = null,
        output: String? = null,
    ): Array<String> {
        return build(
            *mutableListOf("event", "results").apply {
                add("${event.id}")
                addAll(arrayOf("--type", type.key))
                addAll(format?.let { arrayOf("--format", format.name.lowercase()) } ?: emptyArray())
                if (output != null) add("--$output")
            }.toTypedArray()
        )
    }

    fun eventParticipantList(event: Event): Array<String> {
        return build(
            *listOf("event", "participant", "list", "${event.id}").toTypedArray()
        )
    }

    fun eventRunList(event: Event): Array<String> {
        return build("event", "run", "list", "${event.id}")
    }

    fun webappCompetition(
        port: Int? = null,
        exploratory: Boolean? = null
    ): Array<String> {
        return build(
            *buildList {
                addAll(listOf("webapp", "competition"))
                if (port != null) {
                    addAll(listOf("--port", "$port"))
                }
                if (exploratory == true) {
                    add("--exploratory")
                }
            }.toTypedArray()
        )
    }
}