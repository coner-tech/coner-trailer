package tech.coner.trailer.io.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Duration
import java.time.Instant

class LifetimeCache<K, V>(
    private val timeToLive: Duration
) : Cache<K, V> {

    private val store: MutableMap<K, Entry<V>> = mutableMapOf()
    private val mutex = Mutex()

    override suspend fun getOrCreate(key: K, create: () -> V): V {
        return mutex.withLock {
            store[key]
                ?.let {
                    if (it.expires.isAfter(Instant.now())) {
                        it.value
                    } else {
                        store.remove(key)
                        null
                    }
                }
                ?: create()
                    .also { value ->
                        store[key] = createEntry(value)
                    }
        }
    }

    override suspend fun put(key: K, value: V): V {
        mutex.withLock {
            store[key] = createEntry(value)
        }
        return value
    }

    private fun createEntry(value: V): Entry<V> = Entry(
        expires = Instant.now() + timeToLive,
        value = value
    )

    override suspend fun clear() {
        mutex.withLock {
            store.clear()
        }
    }

    override suspend fun prune() {
        mutex.withLock {
            val now = Instant.now()
            store.entries
                .mapNotNull { (key, entry) ->
                    if (entry.expires.isBefore(now)) {
                        key
                    } else {
                        null
                    }
                }
                .forEach { key -> store.remove(key) }
        }
    }

    override suspend fun remove(key: K): V? {
        return mutex.withLock {
            store.remove(key)?.value
        }
    }

    override suspend fun size(): Int {
        return mutex.withLock {
            store.size
        }
    }

    data class Entry<V>(
        val expires: Instant,
        val value: V
    )
}