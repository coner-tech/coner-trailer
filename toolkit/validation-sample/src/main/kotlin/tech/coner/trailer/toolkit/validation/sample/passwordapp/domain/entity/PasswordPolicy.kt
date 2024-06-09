package tech.coner.trailer.toolkit.validation.sample.passwordapp.domain.entity


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
