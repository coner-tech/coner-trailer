package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.extension

@ExperimentalPathApi
class EventAddCommand(
    di: DI,
    useConsole: CliktConsole
) : CliktCommand(
    name = "add",
    help = "Add an Event"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val dbConfig: DatabaseConfiguration by instance()
    private val service: EventService by instance()
    private val view: EventView by instance()

    private val id: UUID by option(hidden = true)
        .convert { toUuid(it) }
        .default(UUID.randomUUID())
    private val name: String by option()
        .required()
    private val date: LocalDate by option()
        .convert {
            try {
                LocalDate.parse(it)
            } catch (dtpe: DateTimeParseException) {
                fail("Invalid date format")
            }
        }
        .required()
    private val crispyFishEventControlFile: Path by option()
        .path(
            mustExist = true,
            canBeFile = true,
            canBeDir = false,
            mustBeReadable = true
        )
        .required()
        .validate {
            if (!it.startsWith(dbConfig.crispyFishDatabase)) {
                fail("Must be within the crispy fish database")
            }
            if (it.extension != "ecf") {
                fail("Must be a .ecf file")
            }
        }
    private val crispyFishClassDefinitionFile: Path by option()
        .path(
            mustExist = true,
            canBeFile = true,
            canBeDir = false,
            mustBeReadable = true
        )
        .required()
        .validate {
            if (!it.startsWith(dbConfig.crispyFishDatabase)) {
                fail("Must be within the crispy fish database")
            }
            if (it.extension != "def") {
                fail("Must be a .def file")
            }
        }

    override fun run() {
        val create = Event(
            id = id,
            name = name,
            date = date,
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = dbConfig.crispyFishDatabase.relativize(crispyFishEventControlFile).toString(),
                classDefinitionFile = dbConfig.crispyFishDatabase.relativize(crispyFishClassDefinitionFile).toString(),
                forceParticipantSignageToPerson = emptyMap()
            )
        )
        service.create(create)
        echo(view.render(create))
    }
}