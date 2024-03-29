package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.hasSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.coner.crispyfish.CrispyFish
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import tech.coner.trailer.io.util.SimpleCache
import java.nio.file.Path

class CrispyFishEventMappingContextServiceTest : CoroutineScope {

    override val coroutineContext = Dispatchers.Default + Job()
    lateinit var service: CrispyFishEventMappingContextService

    @TempDir lateinit var fixtureRoot: Path
    lateinit var fixture: SeasonFixture

    @BeforeEach
    fun before() {
        fixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        service = CrispyFishEventMappingContextService(
            coroutineContext = coroutineContext + Job(),
            nonActiveCache = SimpleCache(),
            crispyFishDatabase = fixtureRoot,
            loadConstraints = CrispyFishLoadConstraints(fixtureRoot),
            crispyFishClassDefinitionsFactory = CrispyFish.classDefinitions,
            crispyFishEventFactory = CrispyFish.event
        )
    }

    @Test
    fun `It should load CrispyFishEventMappingContext`() {
        val event = fixture.events.first().coreSeasonEvent.event

        val actual = runBlocking { service.load(event, event.crispyFish!!) }

        assertThat(actual.allRegistrations).hasSize(7)
    }
}