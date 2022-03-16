package org.coner.trailer.io.mapper

import org.coner.trailer.Club
import org.coner.trailer.datasource.snoozle.entity.ClubEntity

class ClubMapper {

    fun toCore(snoozle: ClubEntity): Club {
        return Club(
            name = snoozle.name
        )
    }

    fun toSnoozle(core: Club): ClubEntity {
        return ClubEntity(
            name = core.name
        )
    }
}