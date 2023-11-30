package tech.coner.trailer.app.admin.command.person

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PersonSearchCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "search",
    help = "Search for people"
) {

    override val diContext = diContextDataSession()
    private val service: PersonService by instance()
    private val adapter: Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    private val clubMemberIdEquals: PersonService.FilterMemberIdEquals? by option("--club-member-id-equals")
        .convert { PersonService.FilterMemberIdEquals(it) }
    private val clubMemberIdContains: PersonService.FilterMemberIdContains? by option("--club-member-id-contains")
        .convert { PersonService.FilterMemberIdContains(it) }
    private val firstNameEquals: PersonService.FilterFirstNameEquals? by option("--first-name-equals")
        .convert { PersonService.FilterFirstNameEquals(it) }
    private val firstNameContains: PersonService.FilterFirstNameContains? by option("--first-name-contains")
        .convert { PersonService.FilterFirstNameContains(it) }
    private val lastNameEquals: PersonService.FilterLastNameEquals? by option("--last-name-equals")
        .convert { PersonService.FilterLastNameEquals(it) }
    private val lastNameContains: PersonService.FilterLastNameContains? by option("--last-name-contains")
        .convert { PersonService.FilterLastNameContains(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val filters = listOfNotNull(
            clubMemberIdEquals,
            clubMemberIdContains,
            firstNameEquals,
            firstNameContains,
            lastNameEquals,
            lastNameContains
        )
            .reduce { acc, filter -> acc.and(filter) }
        val search = service.search(filters)
        echo(view(adapter(search)))
    }
}