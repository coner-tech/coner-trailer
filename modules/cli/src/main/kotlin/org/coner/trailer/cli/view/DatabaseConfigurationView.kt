package org.coner.trailer.cli.view

import org.coner.trailer.cli.io.DatabaseConfiguration

class DatabaseConfigurationView : View<DatabaseConfiguration> {

    override fun render(config: DatabaseConfiguration) = """
    ${config.name} ${if (config.default) "[Default]" else ""}
        Crispy Fish:        ${config.crispyFishDatabase}
        Snoozle:            ${config.snoozleDatabase}
""".trimIndent()

}