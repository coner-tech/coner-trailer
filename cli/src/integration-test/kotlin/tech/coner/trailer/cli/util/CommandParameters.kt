package tech.coner.trailer.cli.util

import java.nio.file.Path
import tech.coner.trailer.presentation.di.Format

data class CommandParameters(
    val configDir: Path,
    val database: String?,
    val motorsportRegUsername: String?,
    val motorsportRegPassword: String?,
    val motorsportRegOrganizationId: String?,
    val format: Format?,
    val verbose: Boolean?,
    val help: Boolean?,
    val subcommandArguments: SubcommandArguments?,
    val subcommandEnvironmentVariables: SubcommandEnvironmentVariables?
) {
    class Builder(
        var configDir: Path,
        var database: String? = null,
        var motorsportRegUsername: String? = null,
        var motorsportRegPassword: String? = null,
        var motorsportRegOrganizationId: String? = null,
        var format: Format? = null,
        var verbose: Boolean? = null,
        var help: Boolean? = null,
        var subcommandArguments: SubcommandArguments? = null,
        var subcommandEnvironmentVariables: SubcommandEnvironmentVariables? = null
    )

    companion object {
        fun builder(configDir: Path, fn: Builder.() -> Unit): CommandParameters {
            return Builder(configDir = configDir)
                .apply(fn)
                .let {
                    CommandParameters(
                        configDir = it.configDir,
                        database = it.database,
                        motorsportRegUsername = it.motorsportRegUsername,
                        motorsportRegPassword = it.motorsportRegPassword,
                        motorsportRegOrganizationId = it.motorsportRegOrganizationId,
                        format = it.format,
                        verbose = it.verbose,
                        help = it.help,
                        subcommandArguments = it.subcommandArguments,
                        subcommandEnvironmentVariables = it.subcommandEnvironmentVariables
                    )
                }
        }
    }
}
