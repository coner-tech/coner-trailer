package org.coner.trailer.cli.util

import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.datasource.crispyfish.fixture.EventFixture
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
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

    fun buildConfigureDatabaseAdd(databaseName: String): Array<String> {
        return build(
            "config", "database", "add",
            "--name", databaseName,
            "--crispy-fish-database", "$crispyFishDir",
            "--snoozle-database", "$snoozleDir",
            "--default",
        )
    }

    fun buildPolicyAdd(
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

    fun buildEventAddCrispyFish(
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

    fun buildEventResultsOverall(
        event: Event,
        report: String,
        format: String? = null,
        output: String? = null,
    ): Array<String> {
        return build(
            *mutableListOf("event", "results", "overall").apply {
                add("${event.id}")
                addAll(arrayOf("--report", report))
                addAll(format?.let { arrayOf("--$format") } ?: emptyArray())
                if (output != null) add("--$output")
            }.toTypedArray()
        )
    }
}