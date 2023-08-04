package tech.coner.trailer.presentation.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel

class TextPersonViews(
    private val lineSeparator: String,
    private val asciiTableFactory: () -> AsciiTable,
) {

    val single = TextView<PersonDetailModel> { model ->
        """
        ID: ${model.id}
        First Name: ${model.firstName}
        Last Name: ${model.lastName}
        Club Member ID: ${model.clubMemberId}
        MotorsportReg Member ID: ${model.motorsportRegId}
    """.trimIndent()
    }

    val collection = TextCollectionView<PersonDetailModel, PersonCollectionModel> { model ->
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First Name", "Last Name", "Club Member ID", "MotorsportReg Member ID")
        at.addRule()
        model.items.forEach { person ->
            at.addRow(
                person.id,
                person.firstName,
                person.lastName,
                person.clubMemberId,
                person.motorsportRegId
            )
            at.addRule()
        }
        at.render()
    }
}