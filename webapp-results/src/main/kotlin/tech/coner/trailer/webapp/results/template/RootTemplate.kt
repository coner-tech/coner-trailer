package tech.coner.trailer.webapp.results.template

import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.TemplatePlaceholder
import io.ktor.server.html.insert
import kotlinx.html.BODY
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

class RootTemplate<CT>(
    val contentTemplateFactory: () -> CT
) : Template<HTML> {
    val title = Placeholder<TITLE>()
    val additionalMeta = Placeholder<META>()
    val nav = Placeholder<NAV>()
    val content = TemplatePlaceholder<BODY>()
    override fun HTML.apply() {
        head {
            meta(name = "viewport", content = "width=device-width, initial-scale=1") {
                script(src = "/assets/bootstrap/bootstrap.bundle.min.js") {
                    integrity = "sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                    attributes["crossorigin"] = "anonymous"
                }
                link(href = "/assets/coner-trailer.css", rel = LinkRel.stylesheet, type = StyleType.textCss)
                insert(additionalMeta)
            }
            title {
                insert(title)
            }
        }
        body {
            classes = setOf("container-xl")
            nav {
                classes = setOf("navbar", "navbar-expand", "navbar-dark", "bg-secondary")
                insert(nav)
            }
            insert(
                template = contentTemplateFactory(),
                placeholder = content
            )
        }
    }
}