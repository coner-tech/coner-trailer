package tech.coner.trailer.toolkit.sample.passwordapp.domain.state

import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.Password
import tech.coner.trailer.toolkit.sample.passwordapp.domain.entity.PasswordPolicy


data class ChangePasswordFormState(
    val passwordPolicy: PasswordPolicy,
    val currentPassword: Password,
    val newPassword: Password,
    val newPasswordRepeated: Password
)

