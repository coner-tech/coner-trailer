package tech.coner.trailer.webapp.results.template

import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.TemplatePlaceholder
import io.ktor.server.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.HEAD
import kotlinx.html.HTML
import kotlinx.html.LinkRel
import kotlinx.html.META
import kotlinx.html.NAV
import kotlinx.html.StyleType
import kotlinx.html.TITLE
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.nav
import kotlinx.html.script

class RootTemplate<CT : Template<FlowContent>>(
    val contentTemplateFactory: () -> CT
) : Template<HTML> {
    val title = Placeholder<TITLE>()
    val additionalMeta = Placeholder<META>()
    val additionalHead = Placeholder<HEAD>()
    val nav = Placeholder<NAV>()
    val content = TemplatePlaceholder<CT>()
    override fun HTML.apply() {
        head {
            meta(name = "viewport", content = "width=device-width, initial-scale=1") {
                insert(additionalMeta)
            }
            script(src = "/assets/bootstrap/bootstrap.bundle.min.js") {
                integrity = "sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                attributes["crossorigin"] = "anonymous"
            }
            link(href = "/assets/coner-trailer.css", rel = LinkRel.stylesheet, type = StyleType.textCss)
            title {
                insert(title)
            }
            insert(additionalHead)
        }
        body {
            classes = setOf("container-xl")
            nav {
                classes = setOf("navbar", "navbar-expand", "navbar-dark", "bg-secondary", "text-white")
                insert(nav)
            }
            insert(contentTemplateFactory(), content)
        }
    }
}