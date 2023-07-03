package tech.coner.trailer.render.property

import tech.coner.trailer.Participant

/**
 * Render the name of the Participant
 */
fun interface ParticipantNamePropertyRenderer : PropertyRenderer<Participant>
fun interface NullableParticipantNamePropertyRenderer : PropertyRenderer<Participant?>