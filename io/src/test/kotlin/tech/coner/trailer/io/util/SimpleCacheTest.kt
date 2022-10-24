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
import kotlinx.coroutines.test.runTest

@ExtendWith(MockKExtension::class)
class SimpleCacheTest : CoroutineScope {

    override val coroutineContext = Job() + Dispatchers.Default

    @Nested
    inner class GetOrCreateTests {

        @Test
        fun `It should get cached value`() = runTest {
            val cache = SimpleCache<String, Int>()
            val key = "one"
            cache.put(key, 1)

            val actual = cache.getOrCreate(key) { fail("cache executed create function but should not have") }

            assertThat(actual).isEqualTo(1)
        }

        @Test
        fun `It should create value and cache if not exists`() = runTest {
            val cache = SimpleCache<String, Int>()
            val key = "key"
            val value = 0

            val first = cache.getOrCreate(key) { value }
            val second = cache.getOrCreate(key) { fail("cache executed create fn but should not have") }

            assertAll {
                assertThat(first).isEqualTo(value)
                assertThat(second).isEqualTo(value)
            }
        }
    }

    @Nested
    inner class UpdateTests {

        @Test
        fun `It should update value and be retrievable`() = runTest {
            val cache = SimpleCache<String, Int>()
            val key = "key"
            val values = listOf(1, 2)
            cache.getOrCreate(key) { values[0] }

            val second = cache.update(key) { values[1] }
            val third = cache.getOrCreate(key) { fail("cache executed create fn but should not have") }

            assertAll {
                assertThat(second).isEqualTo(2)
                assertThat(third).isEqualTo(2)
            }
        }
    }

    @Nested
    inner class PutTests {

        @Test
        fun `It should put value and be retrievable`() = runTest {
            val cache = SimpleCache<String, String>()
            val key = "key"
            val value = "value"

            val first = cache.put(key, value)
            val second = cache.getOrCreate(key) { fail("should not invoke create fn") }

            assertThat(first).all {
                isEqualTo(value)
                isEqualTo(second)
            }
        }
    }

    @Nested
    inner class ClearTests {

        @Test
        fun `It should clear when already empty`() = runTest {
            val cache = SimpleCache<String, String>()
            check(cache.isEmpty()) { "Precondition: cache must initialize empty" }

            cache.clear()

            assertThat(cache.isEmpty()).isTrue()
        }

        @Test
        fun `It should clear when contains items`() = runTest {
            val cache = SimpleCache<Int, Int>()
            for (i in 1..10) {
                cache.put(i, i)
            }
            check(cache.size() == 10) { "Precondition: cache must be populated" }

            cache.clear()

            assertThat(cache.isEmpty()).isTrue()
        }
    }
}