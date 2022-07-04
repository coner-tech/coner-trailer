package tech.coner.trailer.io.util

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import assertk.fail
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Duration
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class LifetimeCacheTest : CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.Default

    @Nested
    inner class GetOrCreateTests {

        @Test
        fun `It should return cached value within valid lifetime`() {
            val cache = LifetimeCache<String, Int>(Duration.ofDays(1))
            val key = "one"
            runBlocking {
                cache.getOrCreate(key) { 1 }
            }

            val actual = runBlocking {
                cache.getOrCreate(key) { fail("cache executed create function but should not have") }
            }

            assertThat(actual).isEqualTo(1)
        }

        @Test
        fun `It should create value when cached value exists but is stale`() {
            val cache = LifetimeCache<String, Int>(Duration.ofMillis(1))
            val key = "two"
            runBlocking {
                cache.getOrCreate(key) { Int.MAX_VALUE }
                delay(10)
            }

            val actual = runBlocking {
                cache.getOrCreate(key) { 2 }
            }

            assertThat(actual).isEqualTo(2)
        }

        @Test
        fun `It should return initially created value within cache lifetime`() {
            val cache = LifetimeCache<String, Int>(Duration.ofSeconds(1))
            val key = "key"
            val random = Random

            val actual = runBlocking {
                (0..100)
                    .map {
                        async {
                            if (it != 0) {
                                delay(random.nextLong(1, 5))
                            }
                            cache.getOrCreate(key) { it }
                        }
                    }
                    .map { it.await() }
                    .distinct()
            }

            assertThat(actual).all {
                hasSize(1)
                index(0).isEqualTo(0)
            }
        }
    }

    @Nested
    inner class PutTests {

        @Test
        fun `It should put value and be retrievable`() {
            val cache = LifetimeCache<String, String>(Duration.ofDays(1))
            val key = "key"
            val value = "value"

            val actual = runBlocking {
                cache.put(key, value)
            }

            assertThat(actual).all {
                isEqualTo(value)
                isEqualTo(runBlocking {
                    cache.getOrCreate(key) { fail("should not invoke create fn") }
                })
            }
        }
    }

    @Nested
    inner class ClearTests {

        @Test
        fun `It should clear when already empty`() {
            val cache = LifetimeCache<String, String>(Duration.ofDays(1))
            check(runBlocking { cache.isEmpty() }) { "Precondition: cache must initialize empty" }

            runBlocking {
                cache.clear()
            }

            assertThat(runBlocking { cache.isEmpty() }).isTrue()
        }

        @Test
        fun `It should clear when contains items`() {
            val cache = LifetimeCache<Int, Int>(Duration.ofDays(1))
            runBlocking {
                for (i in 1..10) {
                    cache.put(i, i)
                }
                check(cache.size() == 10) { "Precondition: cache must be populated" }
            }

            runBlocking {
                cache.clear()
            }

            assertThat(runBlocking { cache.isEmpty() }).isTrue()
        }
    }

    @Nested
    inner class PruneTests {

        @Test
        fun `It should prune expired entries`() {
            val cache = LifetimeCache<Int, Int>(Duration.ofMillis(1))
            runBlocking {
                cache.put(0, 0)
                cache.put(1, 1)
                check(cache.size() == 2) { "Precondition: cache must be populated" }
            }

            runBlocking {
                delay(1)
                cache.prune()
            }

            assertThat(runBlocking { cache.isEmpty() }, "cache empty").isTrue()
        }

        @Test
        fun `It should not prune entries before they expire`() {
            val cache = LifetimeCache<Int, Int>(Duration.ofMillis(10))
            runBlocking {
                cache.put(0, 0)
                delay(9)
                cache.put(1, 1)
                check(cache.size() == 2) { "Precondition: cache must be populated" }
            }

            runBlocking {
                delay(2)
                cache.prune()
            }

            runBlocking {
                assertAll {
                    assertThat(cache.size()).isEqualTo(1)
                    assertThat(cache.getOrCreate(1) { fail("Should not execute create fn") })
                }
            }
        }
    }
}