package tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.model

import assertk.Assert
import assertk.assertions.prop

fun Assert<FooItemModel>.name() = prop(FooItemModel::name)