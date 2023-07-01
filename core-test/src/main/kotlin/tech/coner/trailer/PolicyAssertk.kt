package tech.coner.trailer

import assertk.Assert
import assertk.assertions.prop

fun Assert<Policy>.id() = prop(Policy::id)
fun Assert<Policy>.club() = prop(Policy::club)
fun Assert<Policy>.name() = prop(Policy::name)
fun Assert<Policy>.conePenaltySeconds() = prop(Policy::conePenaltySeconds)
fun Assert<Policy>.paxTimeStyle() = prop(Policy::paxTimeStyle)
fun Assert<Policy>.finalScoreStyle() = prop(Policy::finalScoreStyle)
fun Assert<Policy>.authoritativeParticipantDataSource() = prop(Policy::authoritativeParticipantDataSource)
fun Assert<Policy>.authoritativeRunDataSource() = prop(Policy::authoritativeRunDataSource)
fun Assert<Policy>.topTimesEventResultsMethod() = prop(Policy::topTimesEventResultsMethod)
fun Assert<Policy>.signageStyle() = prop(Policy::signageStyle)