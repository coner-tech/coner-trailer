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
    private val name: String? by option(help = "Name of the Event")
    private val date: LocalDate? by option(
        help = "Date of the Event"
    ).convert { toLocalDate(it) }
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

    private val forcePerson: ForcePersonOptionGroup? by option(
        help = "Add or remove a \"force person\" override record. Only use with \"--crispy-fish set\"."
    )
        .groupChoice(
            "add" to ForcePersonOptionGroup.Add(),
            "remove" to ForcePersonOptionGroup.Remove()
        )

    sealed class ForcePersonOptionGroup(prefix: String) : OptionGroup() {
        val groupingType: TypeChoice by option(
            names = arrayOf("--$prefix-grouping-type"),
            help = "Grouping singular (CS) vs paired (NOV CS)"
        ).choice(
            "singular" to TypeChoice.SINGULAR,
            "paired" to TypeChoice.PAIRED
        ).required()
        val groupingAbbreviationSingular: String? by option(
            names = arrayOf("--$prefix-grouping-abbreviation-singular"),
            help = "Abbreviation of singular grouping (CS)"
        )
        val groupingAbbreviationPaired: Pair<String, String>? by option(
            names = arrayOf("--$prefix-grouping-abbreviation-paired"),
            help = "Abbreviation of paired grouping (NOV CS)"
        ).pair()
        val number: String by option(
            names = arrayOf("--$prefix-number"),
            help = "Number of the participant"
        ).required()
        val personId: UUID by option(
            names = arrayOf("--$prefix-person-id"),
            help = "Person ID to force onto participant whose registration matches the given signage"
        ).convert { toUuid(it) }.required()

        enum class TypeChoice {
            SINGULAR,
            PAIRED
        }

        class Add : ForcePersonOptionGroup(prefix = "add")
        class Remove : ForcePersonOptionGroup(prefix = "remove")
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
                    forcePeople = event.crispyFish?.forcePeople ?: emptyMap()
                )
            }
            is CrispyFishOptions.Unset -> null
            else -> event.crispyFish
        }
        fun buildForcePersonPair(
            forcePersonOptions: ForcePersonOptionGroup
        ): Pair<Participant.Signage, Person> {
            val grouping = when (forcePersonOptions.groupingType) {
                ForcePersonOptionGroup.TypeChoice.SINGULAR -> groupingService.findSingular(
                    crispyFishClassDefinitionFile = requireNotNull(crispyFishPhaseOne).classDefinitionFile,
                    abbreviation = requireNotNull(forcePersonOptions.groupingAbbreviationSingular) {
                        "Missing --[add|remove]-grouping-abbreviation-singular"
                    }
                )
                ForcePersonOptionGroup.TypeChoice.PAIRED -> groupingService.findPaired(
                    crispyFishClassDefinitionFile = requireNotNull(crispyFishPhaseOne).classDefinitionFile,
                    abbreviations = requireNotNull(forcePersonOptions.groupingAbbreviationPaired) {
                        "Missing --[add|remove]-grouping-abbreviation-paired"
                    }
                )
            }
            val signage = Participant.Signage(
                grouping = grouping,
                number = forcePersonOptions.number
            )
            val person = personService.findById(forcePersonOptions.personId)
            return signage to person
        }
        val crispyFishPhaseTwo = if (crispyFish is CrispyFishOptions.Set) {
            when (val forceParticipantsOptions = forcePerson) {
                is ForcePersonOptionGroup.Add -> {
                    requireNotNull(crispyFishPhaseOne).copy(
                        forcePeople = crispyFishPhaseOne.forcePeople
                            .toMutableMap()
                            .apply {
                                val pair = buildForcePersonPair(forceParticipantsOptions)
                                put(pair.first, pair.second)
                            }
                    )
                }
                is ForcePersonOptionGroup.Remove -> {
                    requireNotNull(crispyFishPhaseOne).copy(
                        forcePeople = crispyFishPhaseOne.forcePeople
                            .toMutableMap()
                            .apply {
                                val (signage, person) = buildForcePersonPair(forceParticipantsOptions)
                                val actual = get(signage)
                                if (actual?.id != person.id) {
                                    echo("Person ID given in options doesn't match force person record's signage")
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