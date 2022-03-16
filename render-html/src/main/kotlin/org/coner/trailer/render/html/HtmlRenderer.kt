package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.render.Renderer

interface HtmlRenderer : Renderer {

    fun HEAD.installBootstrap() {
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
        script(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js") {
            integrity = "sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            attributes["crossorigin"] = "anonymous"
        }
    }

    fun HEAD.installConerTrailerStylesheet() {
        val css = HtmlRenderer::class.java.getResourceAsStream("coner-trailer.css")
            .bufferedReader()
            .readText()
        style(type = StyleType.textCss) {
            unsafe { raw(css) }
        }
    }
}