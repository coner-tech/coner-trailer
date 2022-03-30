package tech.coner.trailer.io

data class Configuration(
    val databases: Map<String, DatabaseConfiguration>,
    var defaultDatabaseName: String?
) {
    companion object {
        val DEFAULT = Configuration(
            databases = emptyMap(),
            defaultDatabaseName = null
        )
    }
}