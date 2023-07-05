package tech.coner.trailer.render.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Person
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.view.BaseCollectionViewRenderer
import tech.coner.trailer.render.view.PersonCollectionViewRenderer

class TextPersonViewRenderer(
    lineSeparator: String,
    private val asciiTableFactory: () -> AsciiTable,
    private val personIdPropertyRenderer: PersonIdPropertyRenderer,
    private val personFirstNamePropertyRenderer: PersonFirstNamePropertyRenderer,
    private val personLastNamePropertyRenderer: PersonLastNamePropertyRenderer,
    private val personClubMemberIdPropertyRenderer: PersonClubMemberIdPropertyRenderer,
    private val personMotorsportRegMemberIdPropertyRenderer: PersonMotorsportRegMemberIdPropertyRenderer
) : BaseCollectionViewRenderer<Person>(
    lineSeparator = lineSeparator
), PersonCollectionViewRenderer {

    override fun render(model: Person) = """
        ID: ${personIdPropertyRenderer.render(model)}
        First Name: ${personFirstNamePropertyRenderer(model)}
        Last Name: ${personLastNamePropertyRenderer.render(model)}
        Club Member ID: ${personClubMemberIdPropertyRenderer.render(model)}
        MotorsportReg Member ID: ${personMotorsportRegMemberIdPropertyRenderer.render(model)}
    """.trimIndent()

    override fun render(models: Collection<Person>): String {
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First Name", "Last Name", "Club Member ID", "MotorsportReg Member ID")
        at.addRule()
        models.forEach { model ->
            at.addRow(
                personIdPropertyRenderer.render(model),
                personFirstNamePropertyRenderer.render(model),
                personLastNamePropertyRenderer.render(model),
                personClubMemberIdPropertyRenderer.render(model),
                personMotorsportRegMemberIdPropertyRenderer.render(model)
            )
            at.addRule()
        }
        return at.render()
    }
}