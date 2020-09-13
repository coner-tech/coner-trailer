package org.coner.trailer.cli.view

import org.coner.trailer.Person

class PersonView : View<Person> {

    override fun render(model: Person) = """
        ${model.firstName} ${model.lastName}
        ID:
            ${model.id}
        Member ID:
            ${model.memberId}
    """.trimIndent()
}