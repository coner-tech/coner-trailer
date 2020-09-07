package org.coner.trailer.io.constraint

abstract class Constraint<T> {

    abstract fun assess(candidate: T)

    protected inline fun constrain(satisfied: Boolean, message: () -> String) {
        if (!satisfied) throw ConstraintViolationException(message())
    }
}
