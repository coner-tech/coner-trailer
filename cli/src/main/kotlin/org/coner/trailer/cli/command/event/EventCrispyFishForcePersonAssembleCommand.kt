package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventCrispyFishForcePersonAssembleCommand(
    di: DI
) : CliktCommand(
    name = "crispy-fish-force-person-assemble",
    help = "Interactively assemble the crispy fish force person map for an event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val dbConfig: DatabaseConfiguration by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() {
        val event = service.findById(id)
        val crispyFish = event.crispyFish
        if (crispyFish == null) {
            echo("Selected Event lacks Crispy Fish Metadata")
            throw Abort()
        }
        val eventControlFilePath = dbConfig.crispyFishDatabase.resolve(crispyFish.eventControlFile)
        val classDefinitionFilePath = dbConfig.crispyFishDatabase.resolve(crispyFish.classDefinitionFile)
        val context = crispyFishEventMappingContextService.load(
            eventControlFilePath = eventControlFilePath,
            classDefinitionFilePath = classDefinitionFilePath
        )
    }
}