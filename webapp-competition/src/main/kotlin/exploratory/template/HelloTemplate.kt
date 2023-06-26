package exploratory.template

import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.P
import kotlinx.html.p

class HelloTemplate : Template<FlowContent> {
    val to = Placeholder<P>()

    override fun FlowContent.apply() {
        p {
                insert(to)
        }
    }
}