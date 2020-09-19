package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.Person
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.function.Predicate

class PersonSearchCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "search",
        help = "Search for people"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: PersonService by instance()
    private val view: PersonView by instance()

    private val memberIdEquals: PersonService.FilterMemberIdEquals? by option("--member-id-equals")
            .convert { PersonService.FilterMemberIdEquals(it) }
    private val memberIdContains: PersonService.FilterMemberIdContains? by option("--member-id-contains")
            .convert { PersonService.FilterMemberIdContains(it) }
    private val firstNameEquals: PersonService.FilterFirstNameEquals? by option("--first-name-equals")
            .convert { PersonService.FilterFirstNameEquals(it) }
    private val firstNameContains: PersonService.FilterFirstNameContains? by option("--first-name-contains")
            .convert { PersonService.FilterFirstNameContains(it) }
    private val lastNameEquals: PersonService.FilterLastNameEquals? by option("--last-name-equals")
            .convert { PersonService.FilterLastNameEquals(it) }
    private val lastNameContains: PersonService.FilterLastNameContains? by option("--last-name-contains")
            .convert { PersonService.FilterLastNameContains(it) }

    override fun run() {
        val filters: List<Predicate<Person>> = listOfNotNull(
                memberIdEquals,
                memberIdContains,
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