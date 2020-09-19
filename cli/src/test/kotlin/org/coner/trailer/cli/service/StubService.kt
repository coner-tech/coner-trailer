package org.coner.trailer.cli.service

import org.coner.trailer.cli.io.DatabaseConfiguration

class StubService(
        private val databaseConfiguration: DatabaseConfiguration
) {

    fun doSomething() {
        // no-op
    }
}