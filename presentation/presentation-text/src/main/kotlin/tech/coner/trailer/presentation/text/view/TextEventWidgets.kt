package tech.coner.trailer.presentation.text.view

import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.model.EventDetailCollectionModel

class TextEventWidgets(
    private val terminal: Terminal,
    private val asciiTableFactory: () -> AsciiTable,
) {

    val single = TextView<EventDetailModel> { model ->
        """
            ID:         ${model.id}
            Name:       ${model.name}
            Date:       ${model.date}
            Lifecycle:  ${model.lifecycle}
            Crispy Fish:
                    Event Control File:     ${model.crispyFishEventControlFile}
                    Class Definition File:  ${model.crispyFishClassDefinitionFile}
                    People Map:             ${renderPeopleMap(model)}
            MotorsportReg:
                    ID:     ${model.motorsportRegId}
            Policy:
                    ID:     ${model.policyId}
                    Name:   ${model.policyName}
    """.trimIndent()
    }

    val collection = TextCollectionView<EventDetailModel, EventDetailCollectionModel> { model ->
        terminal.render(
            table {
                captionTop("Events")
                header {
                    row("ID", "Name", "Date", "Policy")
                }
                this.body {
                    model.items.forEach { event ->
                        row(
                            event.id,
                            event.name,
                            event.date,
                            event.policyName,
                        )
                    }
                }
            }
        )
    }

    private fun renderPeopleMap(model: EventDetailModel): String {
        val content = if (model.crispyFishPeopleMap.isNotEmpty()) {
            val at = asciiTableFactory()
            at.renderer.cwc = CWC_LongestLine()
            at.addRule()
            at.addRow("Signage", "Person ID", "Person Name")
            at.addRule()
            model.crispyFishPeopleMap.forEach { entry ->
                at.addRow(
                    entry.signage,
                    entry.personId,
                    entry.personName
                )
            }
            at.addRule()
            at.render()
        } else {
            "Empty"
        }
        return buildString {
            appendLine()
            append(content)
        }.prependIndent(" ".repeat(24))
    }
}