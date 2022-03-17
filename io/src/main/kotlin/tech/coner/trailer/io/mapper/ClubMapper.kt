package tech.coner.trailer.io.mapper

import tech.coner.trailer.Club
import tech.coner.trailer.datasource.snoozle.entity.ClubEntity

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