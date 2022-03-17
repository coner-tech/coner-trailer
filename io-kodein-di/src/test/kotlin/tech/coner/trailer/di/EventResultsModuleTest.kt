package tech.coner.trailer.di

import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import tech.coner.trailer.Policy
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.datasource.crispyfish.eventresults.LegacyBuggedPaxTimeRunScoreFactory
import tech.coner.trailer.datasource.crispyfish.eventresults.ParticipantResultMapper
import tech.coner.trailer.eventresults.PaxTimeRunScoreFactory
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path

class EventResultsModuleTest {

    lateinit var di: DirectDI

    @TempDir lateinit var root: Path
    lateinit var context: DataSessionHolderImpl

    @BeforeEach
    fun before() {
        val databaseConfiguration = TestDatabaseConfigurations(root).bar
        di = DI.from(listOf(databaseModule, eventResultsModule))
            .direct
        context = EnvironmentHolderImpl(
            di = di.di,
            configurationServiceArgument = ConfigurationServiceArgument.Default,
            databaseConfiguration = databaseConfiguration,
            motorsportRegCredentialSupplier = { throw UnsupportedOperationException() }
        )
            .let { DataSessionHolderImpl(di.di, it) }
    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a fair type instance`() {
        val policy = TestPolicies.lsccV2
        check(policy.paxTimeStyle == PaxTimeStyle.FAIR) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.on(context).factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(PaxTimeRunScoreFactory::class)
    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a legacy bugged instance`() {
        val policy = TestPolicies.lsccV1
        check(policy.paxTimeStyle == PaxTimeStyle.LEGACY_BUGGED) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.on(context).factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(LegacyBuggedPaxTimeRunScoreFactory::class)
    }

    @Test
    fun `Its ParticipantResultsMapper factory for raw results should not throw`() {
        val policy = TestPolicies.lsccV2

        assertDoesNotThrow {
            di.on(context).factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy)
        }
    }

    @Test
    fun `Its ParticipantResultMapper factories should be unique to results type`() {
        val policy = TestPolicies.lsccV2
        val standardResultsTypes = listOf(
            StandardEventResultsTypes.raw,
            StandardEventResultsTypes.pax,
            StandardEventResultsTypes.clazz
        )

        val actual = standardResultsTypes.map {
            di.on(context).factory<Policy, ParticipantResultMapper>(it).invoke(policy)
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