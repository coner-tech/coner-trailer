package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.io.DatabaseConfiguration

class DatabaseConfigurationView(
        override val console: CliktConsole
) : BaseCollectionView<DatabaseConfiguration>() {

    override fun render(config: DatabaseConfiguration) = """
        |${config.name} ${if (config.default) "[Default]" else ""}
        |    Crispy Fish:        ${config.crispyFishDatabase}
        |    Snoozle:            ${config.snoozleDatabase}
        |    MotorsportReg:      
        |${render(config.motorsportReg)}
        """.trimMargin()

    private fun render(model: DatabaseConfiguration.MotorsportReg?): String = model?.let {
        """
        |        Username:           ${it.username}
        |        Organization ID:    ${it.organizationId}
        """.trimMargin()
    } ?: "null"

}