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
                competition = WebappConfiguration(
                    port = 8080,
                    exploratory = false
                )
            )
        )
    }

    data class Webapps(
        val competition: WebappConfiguration?
    ) {
        fun requireCompetition() = requireNotNull(competition)
    }

    fun requireWebapps() = requireNotNull(webapps)
}