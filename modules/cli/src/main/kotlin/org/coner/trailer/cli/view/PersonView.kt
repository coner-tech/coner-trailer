package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.Person

class PersonView(override val console: CliktConsole) : CollectionView<Person> {

    override fun render(model: Person) = """
        ${model.firstName} ${model.lastName}
        ID:
            ${model.id}
        Member ID:
            ${model.memberId}
    """.trimIndent()
}