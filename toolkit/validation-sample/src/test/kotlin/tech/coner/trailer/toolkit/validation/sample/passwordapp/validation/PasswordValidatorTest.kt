package tech.coner.trailer.toolkit.validation.sample.passwordapp.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.key
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy.Factory.anyOneChar
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy.Factory.irritating
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLength
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterLowercase
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterUppercase
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientNumeric
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientSpecial
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordValidator
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class PasswordValidatorTest {

    private val validator: PasswordValidator = PasswordValidator()

    enum class PasswordScenario(
        val policy: PasswordPolicy,
        val password: String,
        val expectedFeedback: List<PasswordFeedback>? = null,
        val expectedIsValid: Boolean
    ) {
        EMPTY_ANY_ONE_CHAR_INVALID(
            policy = anyOneChar(),
            password = "",
            expectedFeedback = listOf(InsufficientLength(Error)),
            expectedIsValid = false
        ),
        MINIMUM_ANY_ONE_CHAR_VALID(
            policy = anyOneChar(),
            password = "b",
            expectedIsValid = true
        ),
        IRRITATING_INVALID(
            policy = irritating(),
            password = "aA1!",
            expectedFeedback = listOf(
                InsufficientLength(Error),
                InsufficientLetterLowercase(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientNumeric(Warning),
                InsufficientSpecial(Warning)
            ),
            expectedIsValid = false
        ),
        IRRITATING_VALID_WITH_WARNINGS(
            policy = irritating(),
            password = "Tr0ub4dor&3",
            expectedFeedback = listOf(
                InsufficientLength(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientSpecial(Warning)
            ),
            expectedIsValid = true
        ),
        IRRITATING_HARD_TO_REMEMBER_EASY_GUESS_FOR_COMPUTER(
            policy = irritating(),
            password = "battery horse staple correct",
            expectedFeedback = listOf(
                InsufficientLetterUppercase(Error),
                InsufficientNumeric(Error)
            ),
            expectedIsValid = false
        )
    }

    @ParameterizedTest
    @EnumSource(PasswordScenario::class)
    fun itShouldValidatePassword(scenario: PasswordScenario) {
        val actual = validator(scenario.policy, scenario.password)

        assertThat(actual).all {
            feedback().all {
                if (scenario.expectedFeedback?.isNotEmpty() == true) {
                    key(null).isEqualTo(scenario.expectedFeedback)
                } else {
                    isEmpty()
                }
            }
            isValid().isEqualTo(scenario.expectedIsValid)
            isInvalid().isEqualTo(!scenario.expectedIsValid)
        }
    }
}