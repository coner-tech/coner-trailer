package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class CrispyFishClassMapperTest {

    lateinit var mapper: CrispyFishClassMapper

    @BeforeEach
    fun before() {
        mapper = CrispyFishClassMapper()
    }

    @Test
    fun `It should map class definition`() {
        val input = TestClassDefinitions.Lscc2019.CS


        val actual = mapper.toCore(0, input)

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