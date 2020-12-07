package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.groupSwitch
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.cli.util.clikt.toLocalDate
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

class EventSetCommand(
    di: DI
) : CliktCommand(
    name = "set",
    help = "Set an Event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val date: LocalDate? by option().convert { toLocalDate(it) }
    private val crispyFish: CrispyFishOptions? by option()
        .groupSwitch(
            "--set-crispy-fish" to CrispyFishOptions.Set(),
            "--unset-crispy-fish" to CrispyFishOptions.Unset
        )
    sealed class CrispyFishOptions : OptionGroup() {
        class Set : CrispyFishOptions() {
            val eventControlFile: Path? by option().path(
                mustExist = true,
                mustBeReadable = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false
            )
            val classDefinitionFile: Path? by option().path(
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
        val set = event.copy(
            name = name ?: event.name,
            date = date ?: event.date,
            crispyFish = when (val crispyFishOptions = crispyFish) {
                is CrispyFishOptions.Set -> {
                    Event.CrispyFishMetadata(
                        eventControlFile = crispyFishOptions.eventControlFile?.toString()
                            ?: requireNotNull(event.crispyFish?.eventControlFile) { "Missing --event-control-file" },
                        classDefinitionFile = crispyFishOptions.classDefinitionFile?.toString()
                            ?: requireNotNull(event.crispyFish?.classDefinitionFile) { "Missing --class-definition-file"  },
                        forceParticipantSignageToPerson = requireNotNull(event.crispyFish?.forceParticipantSignageToPerson)
                    )
                }
                is CrispyFishOptions.Unset -> null
                else -> event.crispyFish
            }
        )
        service.update(set)
        echo(view.render(set))
    }
}