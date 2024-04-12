package tech.coner.trailer.toolkit.konstraints

import kotlin.reflect.KProperty1

abstract class CompositeConstraint<T> : Constraint<T>() {

    final override fun assess(candidate: T) {
        all.forEach { it(candidate).getOrThrow() }
    }

    private val _propertyConstraints = mutableMapOf<KProperty1<T, *>, MutableList<Constraint<T>>>()
    val propertyConstraints: Map<KProperty1<T, *>, List<Constraint<T>>> by lazy {
        _propertyConstraints
            .map { (key, value) -> key to value.toList() }
            .toMap()
    }

    protected fun propertyConstraint(
        property: KProperty1<T, *>,
        assessFn: (T) -> Boolean,
        violationMessageFn: () -> String
    ): Constraint<T> {
        return constraint(assessFn, violationMessageFn)
            .also {
                val existingConstraintList = _propertyConstraints[property]
                if (existingConstraintList != null) {
                    existingConstraintList += it
                } else {
                    val newConstraintList = mutableListOf<Constraint<T>>()
                    newConstraintList += it
                    _propertyConstraints[property] = newConstraintList
                }
            }
    }

    private val _objectConstraints = mutableListOf<Constraint<T>>()
    val objectConstraints by lazy { _objectConstraints.toList() }

    protected fun objectConstraint(
        assessFn: (T) -> Boolean,
        violationMessageFn: () -> String
    ): Constraint<T> {
        return constraint(assessFn, violationMessageFn)
            .also { _objectConstraints += it }
    }

    val all: Collection<Constraint<T>> by lazy {
        buildList {
            addAll(_propertyConstraints.flatMap { it.value })
            addAll(_objectConstraints)
        }
    }
}