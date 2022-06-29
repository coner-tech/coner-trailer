package tech.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.io.service.PersonService
import java.util.function.Predicate

class PersonSearchCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "search",
        help = "Search for people"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PersonService by instance()
    private val view: PersonView by instance()

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

    override fun run() = diContext.use {
        val filters: List<Predicate<Person>> = listOfNotNull(
                clubMemberIdEquals,
                clubMemberIdContains,
                firstNameEquals,
                firstNameContains,
                lastNameEquals,
                lastNameContains
        )
        val filter = filters.reduce { acc, filter -> acc.and(filter) }
        val search = service.search(filter)
        echo(view.render(search))
    }
}