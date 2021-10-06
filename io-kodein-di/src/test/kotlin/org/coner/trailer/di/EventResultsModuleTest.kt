package org.coner.trailer.di

import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import org.coner.trailer.Policy
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.datasource.crispyfish.eventresults.LegacyBuggedPaxTimeRunScoreFactory
import org.coner.trailer.datasource.crispyfish.eventresults.ParticipantResultMapper
import org.coner.trailer.eventresults.PaxTimeRunScoreFactory
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.eventresults.StandardEventResultsTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path

class EventResultsModuleTest {

    lateinit var di: DirectDI



    @BeforeEach
    fun before() {
        di = DI.from(listOf(eventResultsModule)).direct
    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a fair type instance`() {
        val policy = TestPolicies.lsccV2
        check(policy.paxTimeStyle == PaxTimeStyle.FAIR) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(PaxTimeRunScoreFactory::class)

    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a legacy bugged instance`() {
        val policy = TestPolicies.lsccV1
        check(policy.paxTimeStyle == PaxTimeStyle.LEGACY_BUGGED) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(LegacyBuggedPaxTimeRunScoreFactory::class)
    }

    @Test
    fun `Its ParticipantResultsMapper factory for raw results should not throw`(
        @TempDir root: Path
    ) {
        val databaseConfiguration = TestDatabaseConfigurations(root).bar
        val policy = TestPolicies.lsccV2
        di = DI.direct {
            importAll(eventResultsModule, databaseModule(databaseConfiguration))
        }

        assertDoesNotThrow {
            di.factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy)
        }
    }

    @Test
    fun `Its ParticipantResultMapper factories should be unique to results type`(
        @TempDir root: Path
    ) {
        val databaseConfiguration = TestDatabaseConfigurations(root).bar
        val policy = TestPolicies.lsccV2
        di = DI.direct {
            importAll(eventResultsModule, databaseModule(databaseConfiguration))
        }
        val standardResultsTypes = listOf(
            StandardEventResultsTypes.raw,
            StandardEventResultsTypes.pax,
            StandardEventResultsTypes.clazz
        )

        val actual = standardResultsTypes.map {
            di.factory<Policy, ParticipantResultMapper>(it).invoke(policy)
        }

        assertThat(actual[0], "ParticipantResultMapper for raw")
            .transform { mapper -> actual.count { it == mapper } }
            .isEqualTo(1)
        assertThat(actual[1], "ParticipantResultMapper for pax")
            .transform { mapper -> actual.count { it == mapper } }
            .isEqualTo(1)
        assertThat(actual[2], "ParticipantResultMapper for clazz")
            .transform { mapper -> actual.count { it == mapper } }
            .isEqualTo(1)
    }
}