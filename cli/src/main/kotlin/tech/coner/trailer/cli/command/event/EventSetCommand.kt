package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.groupChoice
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Event
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toLocalDate
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

class EventSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set an Event"
) {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val adapter: EventDetailModelAdapter by instance()
    private val view: TextView<EventDetailModel> by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option(help = "Name of the Event")
    private val date: LocalDate? by option(
        help = "Date of the Event"
    ).convert { toLocalDate(it) }
    private val lifecycle: Event.Lifecycle? by option()
        .choice(
            choices = Event.Lifecycle.values().associateBy { it.name },
            ignoreCase = true
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

    private val motorsportReg: MotorsportRegOptions? by option(
        help = "Whether to set or unset the motorsportreg metadata. Must use \"set\" in conjunction with any motorsportreg-related properties.",
        names = arrayOf("--motorsportreg")
    )
        .groupChoice(
            "set" to MotorsportRegOptions.Set(),
            "unset" to MotorsportRegOptions.Unset
        )
    sealed class MotorsportRegOptions : OptionGroup() {
        class Set : MotorsportRegOptions() {
            val msrEventId: String by option(
                help = "Set the MotorsportReg Event ID (use with \"--motorsportreg set\")"
            ).required()
        }
        object Unset : MotorsportRegOptions()
    }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val event = service.findByKey(id).getOrThrow()
        val crispyFish = when (val crispyFishOptions = crispyFish) {
            is CrispyFishOptions.Set -> {
                Event.CrispyFishMetadata(
                    eventControlFile = crispyFishOptions.eventControlFile
                        ?: requireNotNull(event.crispyFish?.eventControlFile) { "Missing --event-control-file" },
                    classDefinitionFile = crispyFishOptions.classDefinitionFile
                        ?: requireNotNull(event.crispyFish?.classDefinitionFile) { "Missing --class-definition-file" },
                    peopleMap = event.crispyFish?.peopleMap ?: emptyMap()
                )
            }
            is CrispyFishOptions.Unset -> null
            else -> event.crispyFish
        }
        val motorsportReg = when (val motorsportRegOptions = motorsportReg) {
            is MotorsportRegOptions.Set -> Event.MotorsportRegMetadata(
                id = motorsportRegOptions.msrEventId
            )
            MotorsportRegOptions.Unset -> null
            else -> event.motorsportReg
        }
        val set = service.update(event.copy(
            name = name ?: event.name,
            date = date ?: event.date,
            lifecycle = lifecycle ?: event.lifecycle,
            crispyFish = crispyFish ?: event.crispyFish,
            motorsportReg = motorsportReg
        )).getOrThrow()
        service.update(set)
        echo(view(adapter(set)))
    }
}