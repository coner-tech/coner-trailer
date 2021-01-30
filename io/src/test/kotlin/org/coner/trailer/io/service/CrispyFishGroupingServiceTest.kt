package org.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.index
import io.mockk.junit5.MockKExtension
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class CrispyFishGroupingServiceTest {

    lateinit var service: CrispyFishGroupingService

    lateinit var seasonFixture: SeasonFixture
    @TempDir lateinit var fixtureRoot: Path

    @BeforeEach
    fun before() {
        val mapper = CrispyFishGroupingMapper()
        seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        service = CrispyFishGroupingService(
            crispyFishRoot = seasonFixture.classDefinitionFile.file.parentFile,
            mapper = mapper
        )
    }

    @Test
    fun `It should load all singulars`() {
        val event = seasonFixture.events.first().coreSeasonEvent.event

        val actual = service.loadAllSingulars(event.crispyFish!!)

        assertThat(actual).all {
            index(0).all {
                hasAbbreviation("NONE")
                hasName("Unknown")
                hasSort(0)
            }
            index(1).all {
                hasAbbreviation("SS")
                hasName("Super Street")
                hasSort(1)
            }
            index(2).all {
                hasAbbreviation("AS")
                hasName("A Street")
                hasSort(2)
            }
            index(3).all {
                hasAbbreviation("BS")
                hasName("B Street")
                hasSort(3)
            }
            index(10).all {
                hasAbbreviation("HCS")
                hasName("Heritage Classic Street")
                hasSort(10)
            }
            index(22).all {
                hasAbbreviation("ESP")
                hasName("E Street Prepared")
                hasSort(22)
            }
            index(44).all {
                hasAbbreviation("FSAE")
                hasName("Formula SAE")
                hasSort(44)
            }
            index(49).all {
                hasAbbreviation("NOV")
                hasName("Novice")
                hasSort(49)
            }
            index(50).all {
                hasAbbreviation("MAC")
                hasName("Classic American Muscle")
                hasSort(50)
            }
            index(51).all {
                hasAbbreviation("X")
                hasName("Pro Class")
                hasSort(51)
            }
            index(52).all {
                hasAbbreviation("RIDE")
                hasName("Passenger")
                hasSort(52)
            }
        }
    }

    @Test
    fun `It should find singular by abbreviation`() {
        val event = seasonFixture.events.first().coreSeasonEvent.event

        val actual = service.findSingular(
            crispyFish = event.crispyFish!!,
            abbreviation = "CS"
        )

        assertThat(actual).hasName("C Street")
    }

    @Test
    fun `It should find paired by abbreviation`() {
        val event = seasonFixture.events.first().coreSeasonEvent.event

        val actual = service.findPaired(
            crispyFish = event.crispyFish!!,
            abbreviations = "NOV" to "CS"
        )

        assertThat(actual).all {
            first().hasName("Novice")
            second().hasName("C Street")
        }

    }
}