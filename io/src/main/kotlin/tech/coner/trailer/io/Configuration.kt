package tech.coner.trailer.io

class Configuration(
    val databases: MutableMap<String, DatabaseConfiguration>,
    var defaultDatabaseName: String?
)