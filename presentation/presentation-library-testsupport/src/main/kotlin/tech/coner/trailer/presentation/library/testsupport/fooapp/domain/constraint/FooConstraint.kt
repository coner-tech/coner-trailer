package tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint

import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint

class FooConstraint : CompositeConstraint<Foo>() {

    val valueIsInRange = propertyConstraint(
        property = Foo::value,
        assessFn = {
            when (it.value) {
                in Foo.values().indices -> true
                else -> false
            }
        },
        violationMessageFn = { "Value is out of range" }
    )
}