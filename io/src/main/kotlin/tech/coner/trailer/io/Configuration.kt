package tech.coner.trailer.io

data class Configuration(
    val databases: Map<String, DatabaseConfiguration>,
    val defaultDatabaseName: String?,
    val webappResultsConfiguration: WebappConfiguration
) {
    companion object {
        val DEFAULT = Configuration(
            databases = emptyMap(),
            defaultDatabaseName = null,
            webappResultsConfiguration = WebappConfiguration(
                port = 8080
            )
        )
    }
}