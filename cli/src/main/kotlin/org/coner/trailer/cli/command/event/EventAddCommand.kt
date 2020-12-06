package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.groups.provideDelegate
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
    di: DI
) : CliktCommand(
    name = "add",
    help = "Add an Event"
), DIAware {

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

    class CrispyFishOptions : OptionGroup() {

        val eventControlFile: Path by option("--crispy-fish-event-control-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                mustBeReadable = true
            )
            .required()
            .validate {
                if (it.extension != "ecf") {
                    fail("Must be a .ecf file")
                }
            }
        val classDefinitionFile: Path by option("--crispy-fish-class-definition-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                mustBeReadable = true
            )
            .required()
            .validate {
                if (it.extension != "def") {
                    fail("Must be a .def file")
                }
            }
    }
    private val crispyFish: CrispyFishOptions? by CrispyFishOptions().cooccurring()

    override fun run() {
        crispyFish?.also {
            if (!it.eventControlFile.startsWith(dbConfig.crispyFishDatabase)) {
                echo("Event Control File must be within the crispy fish database")
                throw Abort()
            }
            if (!it.classDefinitionFile.startsWith(dbConfig.crispyFishDatabase)) {
                echo("Class Definition File must be within the crispy fish database")
                throw Abort()
            }
        }
        val create = Event(
            id = id,
            name = name,
            date = date,
            crispyFish = crispyFish?.let {
                Event.CrispyFishMetadata(
                    eventControlFile = dbConfig.crispyFishDatabase.relativize(it.eventControlFile).toString(),
                    classDefinitionFile = dbConfig.crispyFishDatabase.relativize(it.classDefinitionFile).toString(),
                    forceParticipantSignageToPerson = emptyMap()
                )
            }
        )
        service.create(create)
        echo(view.render(create))
    }
}