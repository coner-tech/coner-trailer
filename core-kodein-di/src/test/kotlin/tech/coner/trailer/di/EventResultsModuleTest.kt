package tech.coner.trailer.di

import assertk.assertThat
import assertk.assertions.hasClass
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.*
import tech.coner.trailer.Policy
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.eventresults.LegacyBuggedPaxTimeRunScoreFactory
import tech.coner.trailer.eventresults.PaxTimeRunScoreFactory
import tech.coner.trailer.eventresults.PaxTimeStyle

class EventResultsModuleTest {

    lateinit var di: DirectDI
    lateinit var eventResultsSession: EventResultsSession

    @BeforeEach
    fun before() {
        di = internalEventResultsModule
        eventResultsSession = EventResultsSession()
    }

    @AfterEach
    fun after() {
        eventResultsSession.close()
    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a fair type instance`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
            .let {
                it.copy(
                    event = it.event.copy(
                        policy = TestPolicies.lsccV2
                    )
                )
            }
        val policy = eventContext.event.policy
        check(policy.paxTimeStyle == PaxTimeStyle.FAIR) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.on(eventResultsSession).factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(PaxTimeRunScoreFactory::class)
    }

    @Test
    fun `Its PaxTimeRunScoreFactory should create a legacy bugged instance`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val policy = eventContext.event.policy
        check(policy.paxTimeStyle == PaxTimeStyle.LEGACY_BUGGED) {
            "Policy has critically wrong and unexpected paxTimeStyle: ${policy.paxTimeStyle}"
        }

        val actual = di.on(eventResultsSession).factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)

        assertThat(actual).hasClass(LegacyBuggedPaxTimeRunScoreFactory::class)
    }

}