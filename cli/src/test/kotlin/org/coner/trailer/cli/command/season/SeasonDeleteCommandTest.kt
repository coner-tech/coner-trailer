package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.SeasonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

@ExtendWith(MockKExtension::class)
class SeasonDeleteCommandTest : DIAware {

    lateinit var command: SeasonDeleteCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
    }
    override val diContext = diContext { command.diContext.value }

    private val service: SeasonService by instance()

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonDeleteCommand(di, global)
            .context { console = testConsole }
    }

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