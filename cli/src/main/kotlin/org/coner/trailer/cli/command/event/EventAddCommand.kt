package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.cli.command.policy.policySelectOptionGroup
import org.coner.trailer.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toLocalDate
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
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
    private val policyService: PolicyService by instance()

    private val id: UUID? by option(hidden = true)
        .convert { toUuid(it) }
    private val name: String by option()
        .required()
    private val date: LocalDate by option()
        .convert { toLocalDate(it) }
        .required()

    class CrispyFishOptions : OptionGroup() {

        val eventControlFile: Path by option("--crispy-fish-event-control-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false,
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
                canBeSymlink = false,
                mustBeReadable = true
            )
            .required()
            .validate {
                if (it.extension != "def") {
                    fail("Must be a .def file")
                }
            }
    }
    private val crispyFishOptions: CrispyFishOptions by CrispyFishOptions()

    private val motorsportRegEventId: String? by option("--motorsportreg-event-id")
    private val policy: Policy by policySelectOptionGroup { policyService }
        .required()

    override fun run() {
        val create = Event(
            id = id ?: UUID.randomUUID(),
            name = name,
            date = date,
            lifecycle = Event.Lifecycle.CREATE,
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = dbConfig.crispyFishDatabase.relativize(crispyFishOptions.eventControlFile).toString(),
                classDefinitionFile = dbConfig.crispyFishDatabase.relativize(crispyFishOptions.classDefinitionFile).toString(),
                peopleMap = emptyMap() // out of scope for add command
            ),
            motorsportReg = motorsportRegEventId?.let { Event.MotorsportRegMetadata(
                id = it
            ) },
            policy = policy
        )
        service.create(create)
        echo(view.render(create))
    }
}