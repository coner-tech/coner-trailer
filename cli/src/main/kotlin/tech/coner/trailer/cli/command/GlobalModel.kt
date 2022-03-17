package tech.coner.trailer.cli.command

import tech.coner.trailer.di.EnvironmentHolder

class GlobalModel(
    var environment: EnvironmentHolder? = null
) {
    fun requireEnvironment(): EnvironmentHolder {
        return checkNotNull(environment) {
            "Missing environment"
        }
    }
}