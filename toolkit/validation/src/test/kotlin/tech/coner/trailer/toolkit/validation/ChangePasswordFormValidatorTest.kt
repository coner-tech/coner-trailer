package tech.coner.trailer.toolkit.validation

import assertk.all
import assertk.assertThat
import assertk.assertions.doesNotContainKey
import assertk.assertions.isEqualTo
import assertk.assertions.key
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.toolkit.validation.ChangePasswordFormFeedback.*
import tech.coner.trailer.toolkit.validation.PasswordPolicy.Factory.anyOneChar
import tech.coner.trailer.toolkit.validation.PasswordPolicy.Factory.irritating
import tech.coner.trailer.toolkit.validation.Severity.Error
import tech.coner.trailer.toolkit.validation.Severity.Warning

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
            expectedNewPasswordFeedback = listOf(InsufficientLength(Error)),
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
                InsufficientLength(Error),
                InsufficientLetterLowercase(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientNumeric(Warning),
                InsufficientSpecial(Warning)
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
                InsufficientLength(Warning),
                InsufficientLetterUppercase(Warning),
                InsufficientSpecial(Warning)
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
                InsufficientLetterUppercase(Error),
                InsufficientNumeric(Error)
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

data class PasswordPolicy(
    val lengthThreshold: MinimumThreshold,
    val letterLowercaseThreshold: MinimumThreshold,
    val letterUppercaseThreshold: MinimumThreshold,
    val numericThreshold: MinimumThreshold,
    val specialThreshold: MinimumThreshold,
) {
    data class MinimumThreshold(
        val minForError: Int,
        val minForWarning: Int,
    )

    object Factory {
        fun anyOneChar(): PasswordPolicy {
            val zeroLengthAllowed = MinimumThreshold(minForError = 0, minForWarning = 0)
            val oneLengthRequired = MinimumThreshold(minForError = 1, minForWarning = 0)
            return zeroLengthAllowed.let {
                PasswordPolicy(oneLengthRequired, it, it, it, it)
            }
        }
        fun irritating(): PasswordPolicy {
            val length = MinimumThreshold(minForError = 8, minForWarning = 12)
            val encourageComplexity = MinimumThreshold(minForError = 1, minForWarning = 2)
            return encourageComplexity.let {
                PasswordPolicy(length, it, it, it, it)
            }
        }
    }
}

data class ChangePasswordFormState(
    val passwordPolicy: PasswordPolicy,
    val currentPassword: String,
    val newPassword: String,
    val newPasswordRepeated: String
)

sealed class ChangePasswordFormFeedback : Feedback {
    data object MustNotBeEmpty : ChangePasswordFormFeedback() {
        override val severity = Error
    }
    data object NewPasswordSameAsCurrentPassword : ChangePasswordFormFeedback() {
        override val severity = Error
    }
    data class InsufficientLength(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientLetterLowercase(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientLetterUppercase(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientNumeric(override val severity: Severity) : ChangePasswordFormFeedback()
    data class InsufficientSpecial(override val severity: Severity) : ChangePasswordFormFeedback()
    data object RepeatPasswordMismatch : ChangePasswordFormFeedback() {
        override val severity = Error
    }
}

private val changePasswordFormValidator = Validator<ChangePasswordFormState, ChangePasswordFormFeedback> { state ->
    operator fun PasswordPolicy.MinimumThreshold.invoke(
        value: Int,
        feedbackFn: (Severity) -> ChangePasswordFormFeedback
    ): ChangePasswordFormFeedback? {
        val severity = when {
            value < minForError -> Error
            value < minForWarning -> Warning
            else -> null
        }
        return severity?.let(feedbackFn)
    }
    on(ChangePasswordFormState::currentPassword) {
        if (it.isEmpty()) MustNotBeEmpty
        else null
    }
    on(ChangePasswordFormState::newPassword) {
        if (state.currentPassword.isNotEmpty() && it == state.currentPassword) NewPasswordSameAsCurrentPassword
        else null
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.lengthThreshold(it.length, ::InsufficientLength)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.letterLowercaseThreshold(it.count { char -> char.isLowerCase() }, ::InsufficientLetterLowercase)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.letterUppercaseThreshold(it.count { char -> char.isUpperCase() }, ::InsufficientLetterUppercase)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.numericThreshold(it.count { char -> char.isDigit() }, ::InsufficientNumeric)
    }
    on(ChangePasswordFormState::newPassword) {
        state.passwordPolicy.specialThreshold(it.count { char -> !char.isLetterOrDigit() }, ::InsufficientSpecial)
    }
    on(ChangePasswordFormState::newPasswordRepeated) {
        if (state.newPassword != it) RepeatPasswordMismatch
        else null
    }
    null
}