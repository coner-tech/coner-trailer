package tech.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.service.ConfigurationService
import java.nio.file.Path

class ConfigDatabaseAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add",
    help = "Add database configuration"
) {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val name: String by option().required()
    private val crispyFishDatabase: Path by option()
        .path(
            mustExist = true,
            canBeFile = false,
            canBeDir = true,
            mustBeReadable = true
        )
        .required()
    private val snoozleDatabase: Path by option()
        .path(
            mustExist = true,
            canBeFile = false,
            canBeDir = true,
            mustBeReadable = true,
            mustBeWritable = true
        )
        .required()
    class MotorsportRegOptions : OptionGroup() {
        val username: String? by option("--motorsportreg-username")
        val organizationId: String? by option("--motorsportreg-organization-id")
    }
    private val motorsportReg: MotorsportRegOptions by MotorsportRegOptions()
    private val default: Boolean by option().flag()

    override suspend fun CoroutineScope.coRun() {
        service.addDatabase(ConfigAddDatabaseParam(
            name = name,
            crispyFishDatabase = crispyFishDatabase,
            snoozleDatabase = snoozleDatabase,
            motorsportReg = ConfigAddDatabaseParam.MotorsportReg(
                username = motorsportReg.username,
                organizationId = motorsportReg.organizationId
            ),
            default = default
        ))
            .onSuccess { echo(view.render(it.addedDbConfig)) }
            .onFailure {
                echo("Failed to add database: ${it.message}", err = true)
                throw ProgramResult(1)
            }
    }
}