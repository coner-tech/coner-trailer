package tech.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Person

class PersonView(override val console: CliktConsole) : BaseCollectionView<Person>() {

    override fun render(model: Person) = """
        ${model.firstName} ${model.lastName}
        ID:
            ${model.id}
        Club Member ID:
            ${model.clubMemberId}
        MotorsportReg Member ID:
            ${model.motorsportReg?.memberId}
    """.trimIndent()

    override fun render(models: Collection<Person>): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First Name", "Last Name", "Club Member ID", "MotorsportReg Member ID")
        at.addRule()
        models.forEach { model ->
            at.addRow(model.id, model.firstName, model.lastName, model.clubMemberId, model.motorsportReg?.memberId)
            at.addRule()
        }
        return at.render()
    }
}