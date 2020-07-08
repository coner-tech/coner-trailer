package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import org.coner.crispyfish.model.ClassDefinition
import org.coner.trailer.*
import org.junit.jupiter.api.Test

class GroupingMapperTest {

    @Test
    fun `It should map class definition`() {
        val input = TestClassDefinitions.Lscc2019.CS

        val actual = GroupingMapper.map(input)

        assertThat(actual).all {
            isSingular()
            hasAbbreviation(input.abbreviation)
            hasName(input.name)
        }
    }

    @Test
    fun `It should map open class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON

        val actual = GroupingMapper.map(input)

        assertThat(actual).all {
            isSingular()
            hasAbbreviation("HS")
            hasName("H Street")
        }
    }

    @Test
    fun `It should map paxed class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.BRANDY_HUFF

        val actual = GroupingMapper.map(input)

        assertThat(actual).all {
            isPaired().all {
                first().all {
                    isSingular()
                    hasAbbreviation("NOV")
                    hasName("Novice")
                }
                second().all {
                    isSingular()
                    hasAbbreviation("BS")
                    hasName("B Street")
                }
            }
        }
    }
}