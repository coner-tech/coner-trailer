package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toLocalDate
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventSetCommand(
    di: DI
) : CliktCommand(
    name = "set",
    help = "Set an Event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val dbConfig: DatabaseConfiguration by instance()
    private val service: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option(help = "Name of the Event")
    private val date: LocalDate? by option(
        help = "Date of the Event"
    ).convert { toLocalDate(it) }
    private val lifecycle: Event.Lifecycle? by option()
        .choice(
            choices = Event.Lifecycle.values()
                .map { it.name.toLowerCase() to it }
                .toMap()
        )
    private val crispyFish: CrispyFishOptions? by option(
        help = "Whether to set or unset the crispy fish metadata. Must use \"set\" in conjunction with any crispy-fish-related properties."
    )
        .groupChoice(
            "set" to CrispyFishOptions.Set(),
            "unset" to CrispyFishOptions.Unset
        )
    sealed class CrispyFishOptions : OptionGroup() {
        class Set : CrispyFishOptions() {
            val eventControlFile: Path? by option(
                help = "Set crispy fish event control file (use with \"--crispy-fish set\")"
            ).path(
                mustExist = true,
                mustBeReadable = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false
            )
            val classDefinitionFile: Path? by option(
                help = "Set crispy fish class definition file (use with \"--crispy-fish set\")"
            ).path(
                mustExist = true,
                mustBeReadable = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false
            )
        }
        object Unset : CrispyFishOptions()
    }

    override fun run() {
        val event = service.findById(id)
        val crispyFish = when (val crispyFishOptions = crispyFish) {
            is CrispyFishOptions.Set -> {
                Event.CrispyFishMetadata(
                    eventControlFile = crispyFishOptions.eventControlFile?.let {
                        dbConfig.crispyFishDatabase.relativize(it).toString()
                    } ?: requireNotNull(event.crispyFish?.eventControlFile) { "Missing --event-control-file" },
                    classDefinitionFile = crispyFishOptions.classDefinitionFile?.let {
                        dbConfig.crispyFishDatabase.relativize(it).toString()
                    } ?: requireNotNull(event.crispyFish?.classDefinitionFile) { "Missing --class-definition-file" },
                    peopleMap = event.crispyFish?.peopleMap ?: emptyMap()
                )
            }
            is CrispyFishOptions.Unset -> null
            else -> event.crispyFish
        }
        val set = event.copy(
            name = name ?: event.name,
            date = date ?: event.date,
            lifecycle = lifecycle ?: event.lifecycle,
            crispyFish = crispyFish ?: event.crispyFish
        )
        service.update(
            update = set,
            context = set.crispyFish?.let { crispyFishEventMappingContextService.load(it) },
            eventCrispyFishPersonMapVerifierCallback = null
        )
        echo(view.render(set))
    }
}