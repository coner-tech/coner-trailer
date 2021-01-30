package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Person

class CrispyFishPersonMapper {

    fun toCore(crispyFish: Registration, motorsportRegMemberId: String?) = Person(
        clubMemberId = crispyFish.memberNumber,
        firstName = crispyFish.firstName,
        lastName = crispyFish.lastName,
        motorsportReg = motorsportRegMemberId?.let {
            Person.MotorsportRegMetadata(memberId = it)
        }
    )
}