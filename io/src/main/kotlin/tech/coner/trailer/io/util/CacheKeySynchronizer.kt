package tech.coner.trailer.io.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class CacheKeySynchronizer<K> {

    private val store: MutableMap<K, Entry> = mutableMapOf()
    private val storeMutex = Mutex()

    suspend fun <T> synchronizeValue(key: K, block: suspend () -> T): T {
        return getMutex(key)
            .withLock {
                try {
                    block()
                } finally {
                    removeMutex(key)
                }
            }
    }

    suspend fun synchronize(key: K, block: suspend () -> Unit) {
        return getMutex(key)
            .withLock {
                try {
                    block()
                } finally {
                    removeMutex(key)
                }
            }
    }

    private suspend fun getMutex(key: K): Mutex {
        return storeMutex.withLock {
            val entry = store[key]
                ?: Entry()
                    .also { store[key] = it }
            entry.counter++
            entry.mutex
        }
    }

    private suspend fun removeMutex(key: K) {
        storeMutex.withLock {
            val entry = store[key]
                ?.also { it.counter-- }
            if (entry?.counter == 0) {
                store.remove(key)
            }
        }
    }

    private class Entry {
        val mutex: Mutex = Mutex()
        var counter: Int = 0
    }
}