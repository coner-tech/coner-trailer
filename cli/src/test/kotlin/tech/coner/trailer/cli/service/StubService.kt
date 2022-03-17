package tech.coner.trailer.cli.service

import tech.coner.trailer.io.DatabaseConfiguration

class StubService(
        private val databaseConfiguration: DatabaseConfiguration
) {

    fun doSomething() {
        // no-op
    }
}