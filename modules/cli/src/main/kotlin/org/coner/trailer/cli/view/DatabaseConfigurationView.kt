package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.io.DatabaseConfiguration

class DatabaseConfigurationView(
        override val console: CliktConsole
) : CollectionView<DatabaseConfiguration> {

    override fun render(config: DatabaseConfiguration) = """
        ${config.name} ${if (config.default) "[Default]" else ""}
            Crispy Fish:        ${config.crispyFishDatabase}
            Snoozle:            ${config.snoozleDatabase}
        """.trimIndent()

}