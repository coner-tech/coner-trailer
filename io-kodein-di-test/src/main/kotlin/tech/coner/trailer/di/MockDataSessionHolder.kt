package tech.coner.trailer.di

import assertk.Assert
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import assertk.assertions.prop
import org.kodein.di.bindings.ScopeRegistry

class MockDataSessionHolder(
    override val environment: EnvironmentHolder
) : DataSessionHolder {
    override var registry: ScopeRegistry? = null

    var open = true
        private set

    override fun close() {
        open = false
    }
}

fun Assert<MockDataSessionHolder>.open() = prop(MockDataSessionHolder::open)
fun Assert<MockDataSessionHolder>.isOpen() = open().isTrue()
fun Assert<MockDataSessionHolder>.isClosed() = open().isFalse()