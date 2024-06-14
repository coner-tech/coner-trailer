package tech.coner.trailer.toolkit.validation.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.math.exp

class InternalExtensionsTest {

    companion object {
        private val digits: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    enum class CreateOrAppendScenarioWithIntegers(
        val initial: MutableMap<Int?, MutableList<Int>> = mutableMapOf(),
        val key: Int?,
        val item: Int,
        val expected: Map<Int?, List<Int>>
    ) {
        FROM_EMPTY_WITH_KEY_NULL(
            key = null,
            item = 0,
            expected = mapOf(
                null to listOf(
                    0
                )
            )
        ),
        FROM_EMPTY_WITH_KEY_INTEGER(
            key = 0,
            item = 0,
            expected = mapOf(
                0 to listOf(
                    0
                )
            )
        ),
        WITH_NULL_EXISTING(
            initial = mutableMapOf(
                null to mutableListOf(
                    0
                )
            ),
            key = 1,
            item = 1,
            expected = mapOf(
                null to listOf(
                    0
                ),
                1 to listOf(
                    1
                )
            )
        ),
        WITH_INSTANCE_EXISTING(
            initial = mutableMapOf(
                0 to mutableListOf(
                    0
                )
            ),
            key = 1,
            item = 1,
            expected = mapOf(
                0 to listOf(
                    0
                ),
                1 to listOf(
                    1
                )
            )
        )
    }

    @ParameterizedTest
    @EnumSource(CreateOrAppendScenarioWithIntegers::class)
    fun itShouldCreateOrAppendWithIntegers(scenario: CreateOrAppendScenarioWithIntegers) {
        val map = scenario.initial

        map.createOrAppend(scenario.key, scenario.item)

        assertThat(map).isEqualTo(scenario.expected)
    }

    enum class CreateOrAppendScenarioWithIntegerMaps(
        val initial: MutableMap<Int?, MutableList<Int>>,
        val input: Map<Int?, List<Int>>,
        val expected: Map<Int?, List<Int>>
    ) {
        FROM_EMPTY_TO_EMPTY(
            initial = mutableMapOf(),
            input = emptyMap(),
            expected = emptyMap()
        ),
        FROM_EMPTY_TO_NULL_SINGULAR(
            initial = mutableMapOf(),
            input = mapOf(
                null to listOf(
                    0
                )
            ),
            expected = mutableMapOf(
                null to listOf(
                    0
                )
            )
        ),
        FROM_EMPTY_TO_NULL_MULTIPLE(
            initial = mutableMapOf(),
            input = mapOf(
                null to listOf(
                    0,
                    1
                )
            ),
            expected = mutableMapOf(
                null to listOf(
                    0,
                    1
                )
            )
        ),
        FROM_EMPTY_TO_MULTIPLE_KEYS_SINGULAR(
            initial = mutableMapOf(),
            input = mapOf(
                0 to listOf(
                    0
                ),
                1 to listOf(
                    1
                )
            ),
            expected = mapOf(
                0 to listOf(
                    0
                ),
                1 to listOf(
                    1
                )
            )
        ),
        FROM_EMPTY_TO_MULTIPLE_KEYS_MULTIPLE(
            initial = mutableMapOf(),
            input = mapOf(
                0 to listOf(
                    0,
                    0
                ),
                1 to listOf(
                    1,
                    1
                )
            ),
            expected = mapOf(
                0 to listOf(
                    0,
                    0
                ),
                1 to listOf(
                    1,
                    1
                )
            )
        ),
        FROM_NULL_SINGULAR_WITH_EMPTY(
            initial = mutableMapOf(
                null to mutableListOf(
                    0
                )
            ),
            input = emptyMap(),
            expected = mapOf(
                null to listOf(
                    0
                )
            )
        ),
        FROM_NULL_MULTIPLE_WITH_EMPTY(
            initial = mutableMapOf(
                null to mutableListOf(
                    0,
                    1
                )
            ),
            input = emptyMap(),
            expected = mapOf(
                null to listOf(
                    0,
                    1
                )
            )
        ),
        FROM_MULTIPLE_KEYS_SINGULAR_WITH_EMPTY(
            initial = mutableMapOf(
                0 to mutableListOf(
                    0
                ),
                1 to mutableListOf(
                    1
                )
            ),
            input = emptyMap(),
            expected = mapOf(
                0 to listOf(
                    0
                ),
                1 to listOf(
                    1
                )
            )
        ),
        FROM_MULTIPLE_KEYS_MULTIPLE_WITH_EMPTY(
            initial = mutableMapOf(
                0 to mutableListOf(
                    0,
                    0
                ),
                1 to mutableListOf(
                    1,
                    1
                )
            ),
            input = emptyMap(),
            expected = mapOf(
                0 to listOf(
                    0,
                    0
                ),
                1 to listOf(
                    1,
                    1
                )
            )
        ),
        FROM_NULL_SINGULAR_WITH_NULL_SINGULAR(
            initial = mutableMapOf(
                null to mutableListOf(
                    0
                )
            ),
            input = mapOf(
                null to mutableListOf(
                    0
                )
            ),
            expected = mapOf(
                null to listOf(
                    0,
                    0
                )
            )
        ),
        FROM_NULL_SINGULAR_WITH_NULL_MULTIPLE(
            initial = mutableMapOf(
                null to mutableListOf(
                    0
                )
            ),
            input = mapOf(
                null to digits.toList()
            ),
            expected = mapOf(
                null to listOf(
                    0,
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9
                )
            )
        ),
        FROM_MULTIPLE_WITH_MULTIPLE(
            initial = mutableMapOf(
                1 to digits.toMutableList(),
                2 to digits.toMutableList()
            ),
            input = mapOf(
                1 to digits.toList(),
                2 to digits.toList(),
                3 to digits.toList(),
            ),
            expected = mapOf(
                1 to mutableListOf<Int>().apply { addAll(digits.toList()); addAll(digits.toList()) },
                2 to mutableListOf<Int>().apply { addAll(digits.toList()); addAll(digits.toList()) },
                3 to digits.toList()
            )
        )
    }

    @ParameterizedTest
    @EnumSource(CreateOrAppendScenarioWithIntegerMaps::class)
    fun itShouldCreateOrAppendWithIntegerMaps(scenario: CreateOrAppendScenarioWithIntegerMaps) {
        val map = scenario.initial

        map.createOrAppend(scenario.input)

        assertThat(map).isEqualTo(scenario.expected)
    }
}