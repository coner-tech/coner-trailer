package tech.coner.trailer.di

import assertk.Assert
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import assertk.assertions.prop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.bindings.ScopeRegistry
import kotlin.coroutines.CoroutineContext

class MockDataSessionHolder(
    override val environment: EnvironmentHolder
) : DataSessionHolder {

    override val coroutineContext = Dispatchers.Default + Job()

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