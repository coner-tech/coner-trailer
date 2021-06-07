package org.coner.trailer.render

import kotlinx.html.HEAD
import kotlinx.html.LinkRel
import kotlinx.html.link
import kotlinx.html.meta

interface KotlinxHtmlRenderer : Renderer {

    fun HEAD.bootstrapMetaViewport() {
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
    }

    fun HEAD.bootstrapLinkCss() {
        link(
            href = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css",
            rel = LinkRel.stylesheet,
        ) {
            integrity = "sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
            attributes["crossorigin"] = "anonymous"
        }
    }
}