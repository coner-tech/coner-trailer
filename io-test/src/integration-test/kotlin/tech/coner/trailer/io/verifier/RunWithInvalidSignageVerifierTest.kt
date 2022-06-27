package tech.coner.trailer.io.verifier

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isNotEmpty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.*
import tech.coner.trailer.datasource.crispyfish.fixture.EventFixture
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import tech.coner.trailer.di.DataSessionHolder
import tech.coner.trailer.di.EnvironmentHolder
import tech.coner.trailer.io.*
import tech.coner.trailer.io.di.ioTestKodeinDi
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.service.*
import tech.coner.trailer.io.util.ServiceContainer
import java.nio.file.Path
import kotlin.io.path.createDirectories

class RunWithInvalidSignageVerifierTest {

    lateinit var verifier: RunWithInvalidSignageVerifier

    @TempDir lateinit var root: Path
    lateinit var services: ServiceContainer

    @BeforeEach
    fun before() {
        verifier = RunWithInvalidSignageVerifier()
        services = ServiceContainer(root)
    }

    @Test
    fun `With all valid runs it should return no runs`() {
        val seasonFixture = SeasonFixture.Lscc2019Simplified(services.crispyFishRoot)
        val event = arrangeCrispyFishEvent(seasonFixture.event1)
        val (participants, runs) = services.onDataSession { VerifierParams(
            participants = participants.list(event).getOrThrow(),
            runs = runs.list(event).getOrThrow()
        ) }

        val actual = verifier.verify(participants, runs)

        assertThat(actual).isEmpty()
    }

    @Test
    fun `When there are runs with invalid signage it should return those with invalid signage`() {
        val seasonFixture = SeasonFixture.Issue64CrispyFishStagingLinesInvalidSignage(services.crispyFishRoot)
        val event = arrangeCrispyFishEvent(seasonFixture.event)
        val (participants, runs) = services.onDataSession { VerifierParams(
            participants = participants.list(event).getOrThrow(),
            runs = runs.list(event).getOrThrow()
        ) }

        val actual = verifier.verify(participants, runs)

        assertThat(actual).isNotEmpty()
    }

    private data class VerifierParams(
        val participants: List<Participant>,
        val runs: List<Run>
    )

    private fun arrangeCrispyFishEvent(eventFixture: EventFixture): Event {
        return services.onDataSession {
            eventFixture.coreSeasonEvent.event.let {
                clubs.createOrUpdate(it.policy.club.name)
                policies.create(it.policy)
                events.create(
                    id = it.id,
                    name = it.name,
                    date = it.date,
                    crispyFishEventControlFile = it.crispyFish!!.eventControlFile,
                    crispyFishClassDefinitionFile = it.crispyFish!!.classDefinitionFile,
                    motorsportRegEventId = it.motorsportReg?.id,
                    policy = it.policy
                )
            }
        }
    }
}