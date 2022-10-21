package tech.coner.trailer.io

data class Configuration(
    val databases: Map<String, DatabaseConfiguration>,
    val defaultDatabaseName: String?,
    val webappResults: WebappConfiguration
) {
    companion object {
        val DEFAULT = Configuration(
            databases = emptyMap(),
            defaultDatabaseName = null,
            webappResults = WebappConfiguration(
                port = 8080
            )
        )
    }
}