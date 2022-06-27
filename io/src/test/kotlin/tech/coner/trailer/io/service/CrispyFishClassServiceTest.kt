package tech.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.index
import assertk.assertions.key
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.*
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import tech.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class CrispyFishClassServiceTest {

    lateinit var service: CrispyFishClassService

    lateinit var seasonFixture: SeasonFixture
    @TempDir lateinit var fixtureRoot: Path

    @BeforeEach
    fun before() {
        val classMapper = CrispyFishClassMapper()
        val classParentMapper = CrispyFishClassParentMapper()
        seasonFixture = SeasonFixture.Lscc2019Simplified(fixtureRoot)
        service = CrispyFishClassService(
            crispyFishRoot = fixtureRoot.toFile(),
            classMapper = classMapper,
            classParentMapper = classParentMapper
        )
    }

    @Test
    fun `It should load all singulars`() {
        val event = seasonFixture.events.first().coreSeasonEvent.event

        val actual = service.loadAllClasses(event.crispyFish!!.classDefinitionFile)

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
    fun `It should load all by abbreviation`() {
        val event = seasonFixture.events.first().coreSeasonEvent.event

        val actual = service.loadAllByAbbreviation(event.crispyFish!!.classDefinitionFile)

        assertThat(actual).all {
            key("CS").hasName("C Street")
            key("NOV").hasName("Novice")
        }

    }
}