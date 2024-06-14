package tech.coner.trailer.toolkit.sample.passwordapp.domain.state

import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.PasswordPolicy


data class ChangePasswordFormState(
    val passwordPolicy: PasswordPolicy,
    val currentPassword: String,
    val newPassword: String,
    val newPasswordRepeated: String
)

