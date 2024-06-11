package tech.coner.trailer.toolkit.validation.sample.passwordapp.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.doesNotContainKey
import assertk.assertions.isEqualTo
import assertk.assertions.key
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.invoke
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy.Factory.anyOneChar
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity.PasswordPolicy.Factory.irritating
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.state.ChangePasswordFormState
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.*
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.PasswordFeedback.*
import tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.validation.changePasswordFormValidator
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid


class ChangePasswordFormValidatorTest {

    enum class ChangePasswordFormScenario(
        val input: ChangePasswordFormState,
        val expectedCurrentPasswordFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedNewPasswordFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedNewPasswordRepeatedFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedIsValid: Boolean
    ) {
        EMPTY_ANY_ONE_CHAR_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = "",
                newPassword = "",
                newPasswordRepeated = ""
            ),
            expectedCurrentPasswordFeedback = listOf(MustNotBeEmpty),
            expectedNewPasswordFeedback = listOf(NewPasswordFeedback(InsufficientLength(Error))),
            expectedIsValid = false
        ),
        MINIMUM_ANY_ONE_CHAR_VALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = "a",
                newPassword = "b",
                newPasswordRepeated = "b"
            ),
            expectedIsValid = true
        ),
        SAME_ANY_ONE_CHAR_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = "a",
                newPassword = "a",
                newPasswordRepeated = "a"
            ),
            expectedNewPasswordFeedback = listOf(NewPasswordSameAsCurrentPassword),
            expectedIsValid = false
        ),
        REPEAT_MISMATCH_ANY_ONE_CHAR_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = "a",
                newPassword = "b",
                newPasswordRepeated = "c"
            ),
            expectedNewPasswordRepeatedFeedback = listOf(RepeatPasswordMismatch),
            expectedIsValid = false
        ),
        IRRITATING_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = "a",
                newPassword = "aA1!",
                newPasswordRepeated = "aA1!"
            ),
            expectedNewPasswordFeedback = listOf(
                NewPasswordFeedback(InsufficientLength(Error)),
                NewPasswordFeedback(InsufficientLetterLowercase(Warning)),
                NewPasswordFeedback(InsufficientLetterUppercase(Warning)),
                NewPasswordFeedback(InsufficientNumeric(Warning)),
                NewPasswordFeedback(InsufficientSpecial(Warning))
            ),
            expectedIsValid = false
        ),
        IRRITATING_VALID_WITH_WARNINGS(
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = "a",
                newPassword = "Tr0ub4dor&3",
                newPasswordRepeated = "Tr0ub4dor&3"
            ),
            expectedNewPasswordFeedback = listOf(
                NewPasswordFeedback(InsufficientLength(Warning)),
                NewPasswordFeedback(InsufficientLetterUppercase(Warning)),
                NewPasswordFeedback(InsufficientSpecial(Warning))
            ),
            expectedIsValid = true
        ),
        IRRITATING_HARD_TO_REMEMBER_EASY_GUESS_FOR_COMPUTER(
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = "a",
                newPassword = "battery horse staple correct",
                newPasswordRepeated = "battery horse staple correct"
            ),
            expectedNewPasswordFeedback = listOf(
                NewPasswordFeedback(InsufficientLetterUppercase(Error)),
                NewPasswordFeedback(InsufficientNumeric(Error))
            ),
            expectedIsValid = false
        )

    }

    @ParameterizedTest
    @EnumSource
    fun itShouldValidateChangePasswordForm(scenario: ChangePasswordFormScenario) {
        val actual = changePasswordFormValidator(scenario.input)

        assertThat(actual).all {
            feedback().all {
                when (scenario.expectedCurrentPasswordFeedback) {
                    is List<ChangePasswordFormFeedback> -> key(ChangePasswordFormState::currentPassword).isEqualTo(scenario.expectedCurrentPasswordFeedback)
                    else -> doesNotContainKey(ChangePasswordFormState::currentPassword)
                }
                when (scenario.expectedNewPasswordFeedback) {
                    is List<ChangePasswordFormFeedback> -> key(ChangePasswordFormState::newPassword).isEqualTo(scenario.expectedNewPasswordFeedback)
                    else -> doesNotContainKey(ChangePasswordFormState::newPassword)
                }
                when (scenario.expectedNewPasswordRepeatedFeedback) {
                    is List<ChangePasswordFormFeedback> -> key(ChangePasswordFormState::newPasswordRepeated).isEqualTo(scenario.expectedNewPasswordRepeatedFeedback)
                    else -> doesNotContainKey((ChangePasswordFormState::newPasswordRepeated))
                }
            }
            isValid().isEqualTo(scenario.expectedIsValid)
            isInvalid().isEqualTo(!scenario.expectedIsValid)
        }
    }

}
