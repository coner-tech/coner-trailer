package tech.coner.trailer.app.admin.view

import com.github.ajalt.clikt.output.CliktConsole
import tech.coner.trailer.io.DatabaseConfiguration

class DatabaseConfigurationView(
        override val console: CliktConsole
) : BaseCollectionView<DatabaseConfiguration>() {

    override fun render(model: DatabaseConfiguration) = """
        |${model.name} ${if (model.default) "[Default]" else ""}
        |    Crispy Fish:        ${model.crispyFishDatabase}
        |    Snoozle:            ${model.snoozleDatabase}
        |    MotorsportReg:      
        |${render(model.motorsportReg)}
        """.trimMargin()

    private fun render(model: DatabaseConfiguration.MotorsportReg?): String = model?.let {
        """
        |        Username:           ${it.username}
        |        Organization ID:    ${it.organizationId}
        """.trimMargin()
    } ?: "null"

}