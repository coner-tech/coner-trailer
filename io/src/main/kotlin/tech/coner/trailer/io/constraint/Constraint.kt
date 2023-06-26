package tech.coner.trailer.io.constraint

abstract class Constraint<T> {

    abstract fun assess(candidate: T)

    operator fun invoke(candidate: T): Result<T> = try {
        assess(candidate)
        Result.success(candidate)
    } catch (cve: ConstraintViolationException) {
        Result.failure(cve)
    }

    @Deprecated(
        message = "Replace with `constraint(assessFn, violationMessageFn) vals",
        replaceWith = ReplaceWith(""),
    )
    protected inline fun constrain(satisfied: Boolean, message: () -> String) {
        if (!satisfied) throw ConstraintViolationException(message())
    }

    fun <V> constraint(
        assessFn: (V) -> Boolean,
        violationMessageFn: () -> String
    ): Constraint<V> = object : Constraint<V>() {
        override fun assess(candidate: V) {
            if (!assessFn(candidate)) {
                throw ConstraintViolationException(violationMessageFn())
            }
        }
    }
}
