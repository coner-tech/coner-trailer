package tech.coner.trailer.app.admin.command.event

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.command.policy.policySelectOptionGroup
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.handle
import tech.coner.trailer.app.admin.util.clikt.toCrispyFishRelativePath
import tech.coner.trailer.app.admin.util.clikt.toLocalDate
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.di.DataSessionHolder
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.payload.CreateEventPayload
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

class EventAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add",
    help = "Add an Event"
) {

    override val diContext = diContextDataSession()

    private val constraints: EventPersistConstraints by instance()
    private val service: EventService by instance()
    private val view: TextView<EventDetailModel> by instance()
    private val adapter: EventDetailModelAdapter by instance()
    private val policyService: PolicyService by instance()

    private val id: UUID? by option(hidden = true)
        .convert { toUuid(it) }
    private val name: String by option()
        .required()
        .validate { id?.also { id -> handle(constraints.hasUniqueName(id to it)) } }
    private val date: LocalDate by option()
        .convert { toLocalDate(it) }
        .required()

    class CrispyFishOptions(di: DI, findDiContext: () -> DIContext<DataSessionHolder>) : OptionGroup(), DIAware by di {
        override val diContext by lazy { findDiContext() }
        private val constraints: EventPersistConstraints by instance()

        val eventControlFile: Path by option("--crispy-fish-event-control-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false,
                mustBeReadable = true
            )
            .convert { toCrispyFishRelativePath(diContext.value.environment.requireDatabaseConfiguration(), it) }
            .required()
            .validate {
                handle(constraints.hasEventControlFileAsRelativePath(it))
                handle(constraints.hasEventControlFileExists(it))
                handle(constraints.hasEventControlFileExtension(it))
            }
        val classDefinitionFile: Path by option("--crispy-fish-class-definition-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false,
                mustBeReadable = true
            )
            .convert { toCrispyFishRelativePath(diContext.value.environment.requireDatabaseConfiguration(), it) }
            .required()
            .validate {
                handle(constraints.hasClassDefinitionFileAsRelativePath(it))
                handle(constraints.hasClassDefinitionFileExists(it))
                handle(constraints.hasClassDefinitionFileExtension(it))
            }
    }
    private val crispyFishOptions: CrispyFishOptions by CrispyFishOptions(di) { diContext }

    private val motorsportRegEventId: String? by option("--motorsportreg-event-id")
    private val policy: Policy by policySelectOptionGroup { policyService }
        .required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val created = service.create(CreateEventPayload(
            id = id,
            name = name,
            date = date,
            crispyFishEventControlFile = crispyFishOptions.eventControlFile,
            crispyFishClassDefinitionFile = crispyFishOptions.classDefinitionFile,
            motorsportRegEventId = motorsportRegEventId,
            policy = policy
        ))
            .getOrThrow()
        echo(view(adapter(created)))
    }
}