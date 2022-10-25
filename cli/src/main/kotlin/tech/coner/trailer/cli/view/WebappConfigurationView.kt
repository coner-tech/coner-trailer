package tech.coner.trailer.cli.view

import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.WebappConfiguration

class WebappConfigurationView : View<Pair<Webapp, WebappConfiguration>> {

    override fun render(model: Pair<Webapp, WebappConfiguration>): String {
        val (webapp, config) = model
        return """
            Webapp: ${webapp.name}
            Config:
                Port: ${config.port}
        """.trimIndent()
    }
}