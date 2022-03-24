package tech.coner.trailer.io

data class Configuration(
    val databases: Map<String, DatabaseConfiguration>,
    var defaultDatabaseName: String?
)