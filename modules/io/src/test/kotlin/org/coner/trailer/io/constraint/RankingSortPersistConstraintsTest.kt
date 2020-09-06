package org.coner.trailer.io.constraint

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.seasonpoints.RankingSort
import org.coner.trailer.seasonpoints.TestRankingSorts
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class RankingSortPersistConstraintsTest {

    lateinit var constraints: RankingSortPersistConstraints

    @MockK
    lateinit var resource: RankingSortResource
    lateinit var mapper: RankingSortMapper

    @BeforeEach
    fun before() {
        mapper = RankingSortMapper()
        constraints = RankingSortPersistConstraints(
                resource = resource,
                mapper = mapper
        )
    }

    @Test
    fun `It should assess valid candidate`() {
        val rankingSorts = Stream.of(
                mapper.toSnoozle(TestRankingSorts.lscc),
                mapper.toSnoozle(TestRankingSorts.olscc)
        )
        every { resource.stream(any()) } returns rankingSorts
        val candidate = RankingSort(
                name = "candidate",
                steps = listOf(RankingSort.Step.ScoreDescending())
        )

        assertDoesNotThrow {
            constraints.assess(candidate)
        }
    }

    @Test
    fun `It should check name is unique`() {
        val original = mapper.toSnoozle(TestRankingSorts.lscc)
        every { resource.stream(any()) } answers  { Stream.of(original) }
        val candidateWithDuplicateName = RankingSortEntity(
                name = TestRankingSorts.lscc.name,
                steps = emptyList()
        )
        val candidateWithUniqueName = RankingSortEntity(
                name = "candidateWithUniqueName",
                steps = emptyList()
        )

        val actualForDuplicate = constraints.hasUniqueName(
                id = candidateWithDuplicateName.id,
                name = candidateWithDuplicateName.name
        )
        val actualForUnique = constraints.hasUniqueName(
                id = candidateWithUniqueName.id,
                name = candidateWithUniqueName.name
        )

        assertAll {
            assertThat(actualForDuplicate).isFalse()
            assertThat(actualForUnique).isTrue()
        }
    }
}