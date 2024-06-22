package tech.coner.trailer.toolkit.sample.fooapp.presentation.model

import assertk.Assert
import assertk.assertions.prop

fun Assert<FooModel>.name() = prop(FooModel::name)