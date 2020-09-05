package org.coner.trailer.io.service

interface Service {

    fun constrain(constraintSatisfied: Boolean, message: (() -> String)? = null) {
        if (!constraintSatisfied) throw ServiceConstraintException(message?.invoke())
    }
}