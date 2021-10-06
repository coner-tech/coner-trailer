package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CrispyFishClassMapperTest {

    lateinit var mapper: CrispyFishClassMapper

    @BeforeEach
    fun before() {
        mapper = CrispyFishClassMapper()
    }

    @Test
    fun `It should map class definition`() {
        val input = TestClassDefinitions.Lscc2019.CS
        val allClassParentsByName = mapOf(
            TestClasses.Lscc2019.STREET.name to TestClasses.Lscc2019.STREET,
            TestClasses.Lscc2019.STREET_TOURING.name to TestClasses.Lscc2019.STREET_TOURING
        )

        val actual = mapper.toCore(allClassParentsByName, 0, input)

        assertThat(actual).all {
            hasAbbreviation(input.abbreviation)
            hasName(input.name)
            sort().isEqualTo(0)
            parent().isNotNull().name().isEqualTo(input.groupName)
            paxFactor().isSameAs(input.paxFactor)
            paxed().isFalse()
        }
    }
}