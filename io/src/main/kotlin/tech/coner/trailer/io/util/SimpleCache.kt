package tech.coner.trailer.io.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SimpleCache<K, V> : Cache<K, V> {

    private val store: MutableMap<K, V> = mutableMapOf()
    private val storeMutex = Mutex()
    private val cacheKeySynchronizer = CacheKeySynchronizer<K>()

    override suspend fun getOrCreate(key: K, create: suspend () -> V): V {
        return cacheKeySynchronizer.synchronizeValue(key) {
            storeMutex.withLock { store[key] }
                ?: create()
                    .also { storeMutex.withLock { store[key] = it } }
        }
    }

    override suspend fun put(key: K, value: V): V {
        cacheKeySynchronizer.synchronize(key) {
            storeMutex.withLock {
                store[key] = value
            }
        }
        return value
    }

    override suspend fun clear() {
        storeMutex.withLock {
            store.clear()
        }
    }

    override suspend fun prune() {
        // no-op
    }

    override suspend fun remove(key: K): V? {
        return cacheKeySynchronizer.synchronizeValue(key) {
            storeMutex.withLock {
                store.remove(key)
            }
        }
    }

    override suspend fun size(): Int {
        return storeMutex.withLock {
            store.size
        }
    }
}