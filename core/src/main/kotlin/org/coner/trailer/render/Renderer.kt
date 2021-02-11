package org.coner.trailer.render

import org.coner.trailer.Participant

interface Renderer {

    fun render(signage: Participant.Signage) = "${signage.grouping.abbreviation} ${signage.number}"
    fun renderName(participant: Participant) = "${participant.firstName} ${participant.lastName}"
}