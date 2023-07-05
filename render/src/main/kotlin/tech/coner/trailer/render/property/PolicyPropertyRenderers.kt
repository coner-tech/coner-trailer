package tech.coner.trailer.render.property

import tech.coner.trailer.Policy

fun interface PolicyIdPropertyRenderer : PropertyRenderer<Policy>
fun interface PolicyNamePropertyRenderer : PropertyRenderer<Policy>
fun interface PolicyConePenaltySecondsPropertyRenderer : PropertyRenderer<Policy>
fun interface PolicyPaxTimeStylePropertyRenderer : PropertyRenderer<Policy>
fun interface PolicyFinalScoreStylePropertyRenderer : PropertyRenderer<Policy>