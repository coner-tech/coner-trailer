package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.hasSize
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class CrispyFishEventMappingContextServiceTest {

    lateinit var service: CrispyFishEventMappingContextService

    lateinit var fixture: SeasonFixture
    @TempDir lateinit var fixtureRoot: Path

    @BeforeEach
    fun before() {
        fixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        service = CrispyFishEventMappingContextService(
            crispyFishDatabase = fixtureRoot,
            loadConstraints = CrispyFishLoadConstraints(fixtureRoot)
        )
    }

    @Test
    fun `It should load CrispyFishEventMappingContext`() {
        val event = fixture.events.first().coreSeasonEvent.event

        val actual = service.load(event.crispyFish!!)

        assertThat(actual.allRegistrations).hasSize(7)
    }
}