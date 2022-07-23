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
class SimpleCacheTest : CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.Default

    @Nested
    inner class GetOrCreateTests {

        @Test
        fun `It should get cached value`() {
            val cache = SimpleCache<String, Int>()
            val key = "one"
            runBlocking {
                cache.put(key, 1)
            }

            val actual = runBlocking {
                cache.getOrCreate(key) { fail("cache executed create function but should not have") }
            }

            assertThat(actual).isEqualTo(1)
        }

        @Test
        fun `It should create value and cache if not exists`() {

        }
    }

    @Nested
    inner class PutTests {

        @Test
        fun `It should put value and be retrievable`() {
            val cache = SimpleCache<String, String>()
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
            val cache = SimpleCache<String, String>()
            check(runBlocking { cache.isEmpty() }) { "Precondition: cache must initialize empty" }

            runBlocking {
                cache.clear()
            }

            assertThat(runBlocking { cache.isEmpty() }).isTrue()
        }

        @Test
        fun `It should clear when contains items`() {
            val cache = SimpleCache<Int, Int>()
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
}