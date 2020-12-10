package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toLocalDate
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
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

    private val dbConfig: DatabaseConfiguration by instance()
    private val service: EventService by instance()
    private val groupingService: CrispyFishGroupingService by instance()
    private val personService: PersonService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val date: LocalDate? by option().convert { toLocalDate(it) }
    private val crispyFish: CrispyFishOptions? by option()
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

    val forceParticipants: ForceParticipantsOptionGroup? by option()
        .groupChoice(
            "add" to ForceParticipantsOptionGroup.Add(),
            "remove" to ForceParticipantsOptionGroup.Remove()
        )

    sealed class ForceParticipantsOptionGroup : OptionGroup() {
        val type: TypeChoice by option()
            .choice(
                "singular" to TypeChoice.SINGULAR,
                "paired" to TypeChoice.PAIRED
            )
            .required()
        val abbreviationSingular: String? by option()
        val abbreviationPaired: Pair<String, String>? by option().pair()
        val number: String by option().required()
        val personId: UUID by option()
            .convert { toUuid(it) }
            .required()

        enum class TypeChoice {
            SINGULAR,
            PAIRED
        }

        class Add : ForceParticipantsOptionGroup()
        class Remove : ForceParticipantsOptionGroup()
    }

    override fun run() {
        val event = service.findById(id)
        val crispyFishPhaseOne = when (val crispyFishOptions = crispyFish) {
            is CrispyFishOptions.Set -> {
                Event.CrispyFishMetadata(
                    eventControlFile = crispyFishOptions.eventControlFile?.let {
                        dbConfig.crispyFishDatabase.relativize(it).toString()
                    } ?: requireNotNull(event.crispyFish?.eventControlFile) { "Missing --event-control-file" },
                    classDefinitionFile = crispyFishOptions.classDefinitionFile?.let {
                        dbConfig.crispyFishDatabase.relativize(it).toString()
                    } ?: requireNotNull(event.crispyFish?.classDefinitionFile) { "Missing --class-definition-file" },
                    forceParticipants = event.crispyFish?.forceParticipants ?: emptyMap()
                )
            }
            is CrispyFishOptions.Unset -> null
            else -> event.crispyFish
        }
        private fun buildMapEntryPair(forceParticipantsOptions: ForceParticipantsOptionGroup): Pair<Participant.Signage, Person> {
            val grouping = when (forceParticipantsOptions.type) {
                ForceParticipantsOptionGroup.TypeChoice.SINGULAR -> groupingService.findSingular(
                    crispyFishClassDefinitionFile = crispyFishPhaseOne.classDefinitionFile,
                    abbreviation = requireNotNull(forceParticipantsOptions.abbreviationSingular) {
                        "Missing --abbreviation-singular"
                    }
                )
                ForceParticipantsOptionGroup.TypeChoice.PAIRED -> groupingService.findPaired(
                    crispyFishClassDefinitionFile = requireNotNull(crispyFishPhaseOne).classDefinitionFile,
                    abbreviations = requireNotNull(forceParticipantsOptions.abbreviationPaired) {
                        "Missing --abbreviation-paired"
                    }
                )
            }
            val signage = Participant.Signage(
                grouping = grouping,
                number = forceParticipantsOptions.number
            )
            val person = personService.findById(forceParticipantsOptions.personId)
            return signage to person
        }
        val crispyFishPhaseTwo = if (crispyFish is CrispyFishOptions.Set) {
            when (val forceParticipantsOptions = forceParticipants) {
                is ForceParticipantsOptionGroup.Add -> {
                    requireNotNull(crispyFishPhaseOne).copy(
                        forceParticipants = crispyFishPhaseOne.forceParticipants
                            .toMutableMap()
                            .apply {
                                val pair = buildMapEntryPair(forceParticipantsOptions)
                                put(pair.first, pair.second)
                            }
                    )
                }
                is ForceParticipantsOptionGroup.Remove -> {
                    requireNotNull(crispyFishPhaseOne).copy(
                        forceParticipants = crispyFishPhaseOne.forceParticipants
                            .toMutableMap()
                            .apply {
                                val (signage, person) = buildMapEntryPair(forceParticipantsOptions)
                                val actual = get(signage)
                                if (actual?.id != person.id) {
                                    echo("Person ID given in options doesn't match force participant record by signage")
                                    throw Abort()
                                }
                                remove(signage)
                            }
                    )
                }
                else -> crispyFishPhaseOne
            }
        } else {
            crispyFishPhaseOne
        }
        val set = event.copy(
            name = name ?: event.name,
            date = date ?: event.date,
            crispyFish = crispyFishPhaseTwo
        )
        service.update(set)
        echo(view.render(set))
    }
}