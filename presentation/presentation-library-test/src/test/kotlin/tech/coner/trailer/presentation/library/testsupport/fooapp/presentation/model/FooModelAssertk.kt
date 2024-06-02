package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model

import assertk.Assert
import assertk.assertions.prop

fun Assert<FooModel>.name() = prop(FooModel::name)