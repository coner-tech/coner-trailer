package tech.coner.trailer.render.text.view

import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.Event
import tech.coner.trailer.render.property.EventDatePropertyRenderer
import tech.coner.trailer.render.property.EventIdPropertyRenderer
import tech.coner.trailer.render.property.EventNamePropertyRenderer
import tech.coner.trailer.render.property.PolicyNamePropertyRenderer
import tech.coner.trailer.render.view.EventCollectionViewRenderer

class TextEventCollectionViewRenderer(
    private val terminal: Terminal,
    private val eventIdPropertyRenderer: EventIdPropertyRenderer,
    private val eventNamePropertyRenderer: EventNamePropertyRenderer,
    private val eventDatePropertyRenderer: EventDatePropertyRenderer,
    private val policyNamePropertyRenderer: PolicyNamePropertyRenderer,
) : EventCollectionViewRenderer {

    override fun render(models: Collection<Event>) = terminal.render(
        table {
            captionTop("Events")
            header {
                row("ID", "Name", "Date", "Policy")
            }
            this.body {
                models.forEach { event ->
                    row(
                        eventIdPropertyRenderer(event),
                        eventNamePropertyRenderer(event),
                        eventDatePropertyRenderer(event),
                        policyNamePropertyRenderer(event.policy),
                    )
                }
            }
        }
    )
}