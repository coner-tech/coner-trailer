package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Person

class PersonView(override val console: CliktConsole) : CollectionView<Person> {

    override fun render(model: Person) = """
        ${model.firstName} ${model.lastName}
        ID:
            ${model.id}
        Member ID:
            ${model.memberId}
    """.trimIndent()

    override fun render(models: Collection<Person>): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First Name", "Last Name", "Member ID")
        at.addRule()
        models.forEach { model ->
            at.addRow(model.id, model.firstName, model.lastName, model.memberId)
            at.addRule()
        }
        return at.render()
    }
}