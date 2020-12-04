package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CrispyFishGroupingMapperTest {

    lateinit var mapperCrispyFish: CrispyFishGroupingMapper

    @BeforeEach
    fun before() {
        mapperCrispyFish = CrispyFishGroupingMapper(
                classDefinitions = SeasonFixture.Lscc2019Simplified.classDefinitions
        )
    }

    @Test
    fun `It should map class definition`() {
        val input = TestClassDefinitions.Lscc2019.CS

        val actual = mapperCrispyFish.toCoreSingular(input)

        assertThat(actual).all {
            isSingular()
            hasAbbreviation(input.abbreviation)
            hasName(input.name)
            hasSort(4)
        }
    }

    @Test
    fun `It should map open class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON

        val actual = mapperCrispyFish.toCore(input)

        assertThat(actual).all {
            isSingular()
            hasAbbreviation("HS")
            hasName("H Street")
            hasSort(9)
        }
    }

    @Test
    fun `It should map paxed class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.BRANDY_HUFF

        val actual = mapperCrispyFish.toCore(input)

        assertThat(actual).all {
            isPaired().all {
                hasSort(49)
                first().all {
                    isSingular()
                    hasAbbreviation("NOV")
                    hasName("Novice")
                    hasSort(49)
                }
                second().all {
                    isSingular()
                    hasAbbreviation("BS")
                    hasName("B Street")
                    hasSort(3)
                }
            }
        }
    }
}