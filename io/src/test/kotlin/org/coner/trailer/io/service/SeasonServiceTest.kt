package org.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.datasource.snoozle.SeasonResource
import org.coner.trailer.datasource.snoozle.entity.SeasonEntity
import org.coner.trailer.io.constraint.SeasonDeleteConstraints
import org.coner.trailer.io.constraint.SeasonPersistConstraints
import org.coner.trailer.io.mapper.SeasonMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
class SeasonServiceTest {

    lateinit var service: SeasonService

    @MockK lateinit var resource: SeasonResource
    @MockK lateinit var mapper: SeasonMapper
    @MockK lateinit var persistConstraints: SeasonPersistConstraints
    @MockK lateinit var deleteConstraints: SeasonDeleteConstraints

    @BeforeEach
    fun before() {
        service = SeasonService(
                resource = resource,
                mapper = mapper,
                persistConstraints = persistConstraints,
                deleteConstraints = deleteConstraints
        )
    }

    @Test
    fun `It should create`() {
        val create = TestSeasons.lscc2019
        val createEntity: SeasonEntity = mockk()
        justRun { persistConstraints.assess(create) }
        every { mapper.toSnoozle(create) } returns createEntity
        justRun { resource.create(createEntity) }

        service.create(create)

        verifySequence {
            persistConstraints.assess(create)
            mapper.toSnoozle(create)
            resource.create(createEntity)
        }
    }

    @Test
    fun `It should find by ID`() {
        val find = TestSeasons.lscc2019
        val findKey = SeasonEntity.Key(id = find.id)
        val findEntity: SeasonEntity = mockk()
        every { resource.read(findKey) } returns findEntity
        every { mapper.toCore(findEntity) } returns find

        service.findById(find.id)

        verifySequence {
            resource.read(findKey)
            mapper.toCore(findEntity)
        }
    }

    @Test
    fun `It should list seasons`() {
        val season = TestSeasons.lscc2019
        val seasons = listOf(season)
        val seasonEntity = mockk<SeasonEntity>()
        val seasonsStream = Stream.of(seasonEntity)
        every { resource.stream() } returns seasonsStream
        every { mapper.toCore(seasonEntity) } returns season

        val actual = service.list()

        verifySequence {
            resource.stream()
            mapper.toCore(seasonEntity)
        }
        assertThat(actual).isEqualTo(seasons)
    }

    @Test
    fun `It should update season`() {
        val update = TestSeasons.lscc2019.copy(
                name = "Updated"
        )
        justRun { persistConstraints.assess(update) }
        val updateEntity = mockk<SeasonEntity>()
        every { mapper.toSnoozle(update) } returns updateEntity
        justRun { resource.update(updateEntity) }

        service.update(update)

        verifySequence {
            persistConstraints.assess(update)
            mapper.toSnoozle(update)
            resource.update(updateEntity)
        }
    }

    @Test
    fun `It should delete season`() {
        val delete = TestSeasons.lscc2019
        justRun { deleteConstraints.assess(delete) }
        val deleteEntity = mockk<SeasonEntity>()
        every { mapper.toSnoozle(delete) } returns deleteEntity
        justRun { resource.delete(deleteEntity) }

        service.delete(delete)

        verifySequence {
            deleteConstraints.assess(delete)
            mapper.toSnoozle(delete)
            resource.delete(deleteEntity)
        }
    }
}