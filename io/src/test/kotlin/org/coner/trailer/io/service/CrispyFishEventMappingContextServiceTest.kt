package org.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.io.constraint.CrispyFishLoadConstraints
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class CrispyFishEventMappingContextServiceTest {

    lateinit var service: CrispyFishEventMappingContextService

    lateinit var fixture: SeasonFixture

    @BeforeEach
    fun before() {
        fixture = SeasonFixture.Lscc2019Simplified
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