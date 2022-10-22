package exploratory.template

import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.SPAN
import kotlinx.html.classes
import kotlinx.html.p
import kotlinx.html.span

class HelloTemplate : Template<FlowContent> {
    val to = Placeholder<SPAN>()

    override fun FlowContent.apply() {
        p {
            span {
                classes = setOf("hello")
                text("Hello ")
            }
            span {
                classes = setOf("to")
                insert(to)
            }
        }
    }
}