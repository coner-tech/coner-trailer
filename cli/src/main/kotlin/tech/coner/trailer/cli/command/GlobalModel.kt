package tech.coner.trailer.cli.command

import tech.coner.trailer.cli.di.Invocation
import tech.coner.trailer.di.EnvironmentHolder
import tech.coner.trailer.presentation.di.Format

class GlobalModel(
    val invocation: Invocation,
    var environment: EnvironmentHolder? = null,
    var format: Format = Format.TEXT,
    var verbose: Boolean = false,
) {
    fun requireEnvironment(): EnvironmentHolder {
        return checkNotNull(environment) {
            "Missing environment"
        }
    }
}