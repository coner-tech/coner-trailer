package tech.coner.trailer.cli.command.season

import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.SeasonService

class SeasonDeleteCommandTest : BaseDataSessionCommandTest<SeasonDeleteCommand>() {

    private val service: SeasonService by instance()

    override fun DirectDI.createCommand() = instance<SeasonDeleteCommand>()

    @Test
    fun `It should delete a season`() {
        val delete = TestSeasons.lscc2019
        every { service.findById(delete.id) } returns delete
        justRun { service.delete(delete) }

        command.parse(arrayOf(
                "${delete.id}"
        ))

        verifySequence {
            service.findById(delete.id)
            service.delete(delete)
        }
    }
}