package validation

import assertk.all
import assertk.assertThat
import assertk.assertions.doesNotContainKey
import assertk.assertions.isEqualTo
import assertk.assertions.key
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.Password
import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.PasswordPolicy.Factory.anyOneChar
import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.PasswordPolicy.Factory.irritating
import tech.coner.trailer.toolkit.sample.passwordapp.domain.state.ChangePasswordFormState
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormFeedback
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.MustNotBeEmpty
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.NewPasswordFeedback
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.NewPasswordSameAsCurrentPassword
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormFeedback.RepeatPasswordMismatch
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.ChangePasswordFormValidator
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLength
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterLowercase
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientLetterUppercase
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientNumeric
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordFeedback.InsufficientSpecial
import tech.coner.trailer.toolkit.sample.passwordapp.domain.validation.PasswordValidator
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning
import tech.coner.trailer.toolkit.validation.ValidationOutcome
import tech.coner.trailer.toolkit.validation.invoke
import tech.coner.trailer.toolkit.validation.testsupport.feedback
import tech.coner.trailer.toolkit.validation.testsupport.feedbackByProperty
import tech.coner.trailer.toolkit.validation.testsupport.isInvalid
import tech.coner.trailer.toolkit.validation.testsupport.isValid


class ChangePasswordFormValidatorTest {

    private val passwordValidator: PasswordValidator = mockk()
    private val changePasswordFormValidator = ChangePasswordFormValidator(
        passwordValidator = passwordValidator
    )

    enum class ChangePasswordFormScenario(
        val mockNewPasswordFeedback: List<PasswordFeedback>? = null,
        val input: ChangePasswordFormState,
        val expectedCurrentPasswordFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedLocalNewPasswordFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedNewPasswordRepeatedFeedback: List<ChangePasswordFormFeedback>? = null,
        val expectedIsValid: Boolean
    ) {

        EMPTY_ANY_ONE_CHAR_INVALID(
            mockNewPasswordFeedback = listOf(InsufficientLength(Error)),
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = Password(""),
                newPassword = Password(""),
                newPasswordRepeated = Password("")
            ),
            expectedCurrentPasswordFeedback = listOf(MustNotBeEmpty(ChangePasswordFormState::currentPassword)),
            expectedIsValid = false
        ),
        MINIMUM_ANY_ONE_CHAR_VALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = Password("a"),
                newPassword = Password("b"),
                newPasswordRepeated = Password("b")
            ),
            expectedIsValid = true
        ),
        SAME_ANY_ONE_CHAR_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = Password("a"),
                newPassword = Password("a"),
                newPasswordRepeated = Password("a")
            ),
            expectedLocalNewPasswordFeedback = listOf(NewPasswordSameAsCurrentPassword),
            expectedIsValid = false
        ),
        REPEAT_MISMATCH_ANY_ONE_CHAR_INVALID(
            input = ChangePasswordFormState(
                passwordPolicy = anyOneChar(),
                currentPassword = Password("a"),
                newPassword = Password("b"),
                newPasswordRepeated = Password("c")
            ),
            expectedNewPasswordRepeatedFeedback = listOf(RepeatPasswordMismatch),
            expectedIsValid = false
        ),
        IRRITATING_INVALID(
            mockNewPasswordFeedback = listOf(
                InsufficientLength(Error),
                InsufficientLetterLowercase(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientNumeric(Warning),
                InsufficientSpecial(Warning)
            ),
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = Password("a"),
                newPassword = Password("aA1!"),
                newPasswordRepeated = Password("aA1!")
            ),
            expectedIsValid = false
        ),
        IRRITATING_VALID_WITH_WARNINGS(
            mockNewPasswordFeedback = listOf(
                InsufficientLength(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientSpecial(Warning)
            ),
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = Password("a"),
                newPassword = Password("Tr0ub4dor&3"),
                newPasswordRepeated = Password("Tr0ub4dor&3")
            ),
            expectedIsValid = true
        ),
        IRRITATING_HARD_TO_REMEMBER_EASY_GUESS_FOR_COMPUTER(
            mockNewPasswordFeedback = listOf(
                InsufficientLetterUppercase(Error),
                InsufficientNumeric(Error)
            ),
            input = ChangePasswordFormState(
                passwordPolicy = irritating(),
                currentPassword = Password("a"),
                newPassword = Password("battery horse staple correct"),
                newPasswordRepeated = Password("battery horse staple correct")
            ),
            expectedIsValid = false
        );

        val expectedNewPasswordFeedback: List<ChangePasswordFormFeedback>?
            get() {
                val fromMock = mockNewPasswordFeedback
                    ?.map { passwordFeedback -> NewPasswordFeedback(passwordFeedback) }
                val fromLocal = expectedLocalNewPasswordFeedback
                return if (fromMock != null || fromLocal != null) {
                    mutableListOf<ChangePasswordFormFeedback>().apply {
                        fromMock?.also { addAll(it) }
                        fromLocal?.also { addAll(it) }
                    }
                } else {
                    null
                }
            }
    }

    @ParameterizedTest
    @EnumSource
    fun itShouldValidateChangePasswordForm(scenario: ChangePasswordFormScenario) {
        every {
            passwordValidator(scenario.input.passwordPolicy, scenario.input.newPassword)
        } returns(
            ValidationOutcome(
                scenario.mockNewPasswordFeedback
                    ?: emptyList()
            )
        )

        val actual = changePasswordFormValidator(scenario.input)

        assertThat(actual).all {
            feedbackByProperty().all {
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
