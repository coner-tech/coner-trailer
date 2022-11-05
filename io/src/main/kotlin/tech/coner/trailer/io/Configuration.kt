package tech.coner.trailer.io

data class Configuration(
    val databases: Map<String, DatabaseConfiguration>,
    val defaultDatabaseName: String?,
    val webapps: Webapps?
) {
    companion object {
        val DEFAULT = Configuration(
            databases = emptyMap(),
            defaultDatabaseName = null,
            webapps = Webapps(
                results = WebappConfiguration(
                    port = 8080,
                    exploratory = false,
                    wait = true
                )
            )
        )
    }

    data class Webapps(
        val results: WebappConfiguration?
    ) {
        fun requireResults() = requireNotNull(results)
    }

    fun requireWebapps() = requireNotNull(webapps)
}