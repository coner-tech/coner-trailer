package org.coner.trailer.cli.io

class Configuration(
        val databases: MutableMap<String, DatabaseConfiguration>,
        var defaultDatabaseName: String?
)