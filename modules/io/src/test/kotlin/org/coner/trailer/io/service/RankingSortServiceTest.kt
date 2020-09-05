package org.coner.trailer.io.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.io.constraint.RankingSortPersistConstraints
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        MockKAnnotations.init(this)
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
}