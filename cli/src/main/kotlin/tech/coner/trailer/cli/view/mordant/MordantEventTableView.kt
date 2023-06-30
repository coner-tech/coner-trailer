package tech.coner.trailer.cli.view.mordant

import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.Event
import tech.coner.trailer.cli.view.View

class MordantEventTableView(
    private val terminal: Terminal
) : View<List<Event>> {

    override fun render(model: List<Event>) = terminal.render(
        table {
            captionTop("Events")
            header {
                row("ID", "Name", "Date", "Policy", "Crispy Fish", "MotorsportReg")
            }
            this.body {
                model.forEach { event ->
                    row(event.id, event.name, event.date, event.policy.name, yesOrEmpty(event.crispyFish), yesOrEmpty(event.motorsportReg))
                }
            }
        }
    )

    private fun yesOrEmpty(ref: Any?) = ref?.let { "Yes" } ?: ""
}