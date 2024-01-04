package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.command.BaseExecutableIT
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode
import tech.coner.trailer.app.admin.util.output
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.di.Format

class EventResultsCommandExecutableIT : BaseExecutableIT() {

    @ParameterizedTest
    @MethodSource("tech.coner.trailer.app.admin.util.ParameterSources#provideArgumentsForEventResultsOfAllTypesAndAllFormats")
    fun `It should print event results of all types and all formats`(
        eventResultType: EventResultsType,
        format: Format
    ) {
        val event = TestEvents.Lscc2019Simplified.points1
        val databaseName = "print-event-results-${eventResultType.key}-${format.name}"
        arrange { configDatabaseAdd(databaseName) }
        val seasonFixture = SeasonFixture.Lscc2019Simplified(crispyFishDir)
        arrange { configureDatabaseSnoozleInitialize() }
        arrange { clubSet(event.policy.club) }
        arrange { policyAdd(event.policy) }
        arrange {
            eventAddCrispyFish(
                event = event,
                crispyFishEventControlFile = seasonFixture.event1.ecfPath,
                crispyFishClassDefinitionFile = seasonFixture.classDefinitionPath
            )
        }

        val processOutcome = testCommand {
            eventResults(
                event = event,
                eventResultsType = eventResultType,
                format = format,
                output = "console"
            )
        }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNotNull().all {
                contains(event.name)
                when (format) {
                    Format.TEXT -> {
                        contains(eventResultType.titleShort)
                        doesNotContain("<!DOCTYPE html")
                        doesNotContain("</html>")
                    }

                    Format.JSON -> {
                        startsWith("{")
                        contains(eventResultType.key)
                        endsWith("}")
                    }
                }
            }
            error().isNull()
        }
    }
}