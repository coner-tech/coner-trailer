package tech.coner.trailer.io.util

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
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
}