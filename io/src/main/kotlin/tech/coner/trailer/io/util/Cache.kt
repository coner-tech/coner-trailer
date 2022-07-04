package tech.coner.trailer.io.util

import kotlinx.coroutines.runBlocking

interface Cache<K, V> {

    /**
     * Get a value by key from cache, or in case of a cache miss, create and associate it with key
     */
    suspend fun getOrCreate(key: K, create: () -> V): V

    suspend fun put(key: K, value: V): V

    /**
     * Clear the entire cache
     */
    suspend fun clear()

    /**
     * Prune stale entries from cache
     */
    suspend fun prune()

    /**
     * Remove the entry
     */
    suspend fun remove(key: K): V?

    /**
     * Find the number of entries in cache
     */
    suspend fun size(): Int

    suspend fun isEmpty(): Boolean = runBlocking { size() == 0 }

}