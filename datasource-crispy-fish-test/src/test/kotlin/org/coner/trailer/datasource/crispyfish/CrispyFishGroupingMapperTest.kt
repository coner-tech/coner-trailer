package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotNull
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class CrispyFishGroupingMapperTest {

    lateinit var mapper: CrispyFishGroupingMapper

    lateinit var context: CrispyFishEventMappingContext

    @TempDir
    lateinit var fixtureRoot: Path

    @BeforeEach
    fun before() {
        val seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        mapper = CrispyFishGroupingMapper()
        context = CrispyFishEventMappingContext(
            allClassDefinitions = seasonFixture.classDefinitions,
            allRegistrations = emptyList(),
            allRuns = emptyList(),
            runCount = 0
        )
    }

    @Test
    fun `It should map class definition`() {
        val input = TestClassDefinitions.Lscc2019.CS


        val actual = mapper.toCoreSingular(context, input)

        assertThat(actual).isNotNull().all {
            isSingular()
            hasAbbreviation(input.abbreviation)
            hasName(input.name)
            hasSort(4)
        }
    }

    @Test
    fun `It should map open class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON

        val actual = mapper.toCore(context, input)

        assertThat(actual).isNotNull().all {
            isSingular()
            hasAbbreviation("HS")
            hasName("H Street")
            hasSort(9)
        }
    }

    @Test
    fun `It should map paxed class registrations`() {
        val input = TestRegistrations.Lscc2019Points1.BRANDY_HUFF

        val actual = mapper.toCore(context, input)

        assertThat(actual).isNotNull().all {
            isPaired().all {
                hasSort(49)
                first().isNotNull().all {
                    isSingular()
                    hasAbbreviation("NOV")
                    hasName("Novice")
                    hasSort(49)
                }
                second().isNotNull().all {
                    isSingular()
                    hasAbbreviation("BS")
                    hasName("B Street")
                    hasSort(3)
                }
            }
        }
    }
}