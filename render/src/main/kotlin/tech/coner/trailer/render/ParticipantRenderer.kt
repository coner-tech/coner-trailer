package tech.coner.trailer.render

import tech.coner.trailer.Participant

interface ParticipantRenderer {

    fun render(participants: List<Participant>): String
}