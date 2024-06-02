package tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity

import assertk.Assert
import assertk.assertions.prop

fun Assert<Foo>.id() = prop(Foo::id)

fun Assert<Foo>.name() = prop(Foo::name)