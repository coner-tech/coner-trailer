package tech.coner.trailer.domain.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.key
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestClubs
import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.toolkit.validation.invoke
import tech.coner.trailer.toolkit.validation.testsupport.feedbackByProperty
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class ClubValidatorTest {

    private val validator = ClubValidator()

    @Test
    fun `On valid club it should be valid`() = runTest {
        val actual = validator.invoke(TestClubs.lscc)

        assertThat(actual)
            .isValid()
    }

    @Test
    fun `On club with blank name it should be invalid`() = runTest {
        val club = TestClubs.invalidForBlankName

        val actual = validator.invoke(club)

        assertThat(actual, "feedback for club with blank name").all {
            isInvalid()
            feedbackByProperty()
                .key(Club::name)
                .contains(ClubFeedback.NameMustNotBeBlank)
        }
    }

    @Test
    fun `On club with name exceeding max length it should be invalid`() = runTest {
        TestClubs.invalidForNameExceedingMaxLength.take(100)
            .forEach { club ->
                val actual = validator.invoke(club)

                assertThat(actual, "feedback for club with name with length=${club.name.length} (exceeding max length)").all {
                    isInvalid()
                    feedbackByProperty()
                        .key(Club::name)
                        .contains(ClubFeedback.NameMustNotExceedMaxLength)
                }
            }
    }
}
