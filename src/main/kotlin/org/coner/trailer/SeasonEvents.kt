package org.coner.trailer

import java.util.*

class SeasonEvents(
        val id: UUID = UUID.randomUUID(),
        val seasonId: UUID,
        val numberToEventId: Map<Int, UUID>
)