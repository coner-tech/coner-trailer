package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.datasource.snoozle.RankingSortResource
import tech.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.mapper.RankingSortMapper
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.TestRankingSorts

@ExtendWith(MockKExtension::class)
class RankingSortServiceTest {

    lateinit var service: RankingSortService

    @MockK
    lateinit var resource: RankingSortResource
    @MockK
    lateinit var mapper: RankingSortMapper
    @MockK
    lateinit var persistConstraints: RankingSortPersistConstraints
    @MockK
    lateinit var rankingSort: RankingSort

    @BeforeEach
    fun before() {
        service = RankingSortService(
                resource = resource,
                mapper = mapper,
                persistConstraints = persistConstraints
        )
    }

    @Test
    fun `It should create ranking sort`() {
        every { persistConstraints.assess(rankingSort) } answers { Unit }
        val snoozle: RankingSortEntity = mockk()
        every { mapper.toSnoozle(rankingSort) } returns snoozle
        every { resource.create(snoozle) } answers { Unit }

        service.create(rankingSort)

        verifySequence {
            persistConstraints.assess(rankingSort)
            mapper.toSnoozle(rankingSort)
            resource.create(snoozle)
        }
    }

    @Test
    fun `It should find ranking sort by ID`() {
        val rankingSort = TestRankingSorts.lscc
        val rankingSortEntity: RankingSortEntity = mockk()
        every { resource.read(match { it.id == rankingSort.id }) } returns rankingSortEntity
        every { mapper.fromSnoozle(rankingSortEntity) } returns rankingSort

        val actual = service.findById(rankingSort.id)

        verifySequence {
            resource.read(match { it.id == rankingSort.id })
            mapper.fromSnoozle(rankingSortEntity)
        }
        assertThat(actual).isSameAs(rankingSort)
    }

    @Test
    fun `It should update ranking sort`() {
        val rankingSort = TestRankingSorts.lscc
        val rankingSortEntity: RankingSortEntity = mockk()
        every { mapper.toSnoozle(rankingSort) } returns rankingSortEntity
        every { resource.update(rankingSortEntity) } answers { Unit }

        service.update(rankingSort)

        verifySequence {
            mapper.toSnoozle(rankingSort)
            resource.update(rankingSortEntity)
        }
    }

    @Test
    fun `It should delete ranking sort`(
            @MockK rankingSort: RankingSort,
            @MockK rankingSortSnoozle: RankingSortEntity
    ) {
        every { mapper.toSnoozle(rankingSort) } returns rankingSortSnoozle
        every { resource.delete(rankingSortSnoozle) } answers { Unit }

        service.delete(rankingSort)

        verifySequence {
            mapper.toSnoozle(rankingSort)
            resource.delete(rankingSortSnoozle)
        }
    }
}