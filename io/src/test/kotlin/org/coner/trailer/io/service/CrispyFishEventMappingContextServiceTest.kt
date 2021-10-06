package org.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.hasSize
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.io.constraint.CrispyFishLoadConstraints
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
        val crispyFishDatabase = fixture.classDefinitionFile.file.parentFile.toPath()
        service = CrispyFishEventMappingContextService(
            crispyFishDatabase = crispyFishDatabase,
            loadConstraints = CrispyFishLoadConstraints(crispyFishDatabase)
        )
    }

    @Test
    fun `It should load CrispyFishEventMappingContext`() {
        val event = fixture.events.first().coreSeasonEvent.event

        val actual = service.load(event.crispyFish!!)

        assertThat(actual.allRegistrations).hasSize(7)
    }
}