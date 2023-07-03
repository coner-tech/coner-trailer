package tech.coner.trailer.render.property

import tech.coner.trailer.Time

fun interface TimePropertyRenderer : PropertyRenderer<Time>
fun interface NullableTimePropertyRenderer : PropertyRenderer<Time?>