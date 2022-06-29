package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Person

class CrispyFishPersonMapper {

    fun toCore(crispyFish: Registration, motorsportRegMemberId: String?) = Person(
        clubMemberId = crispyFish.memberNumber,
        firstName = crispyFish.firstName ?: "",
        lastName = crispyFish.lastName ?: "",
        motorsportReg = motorsportRegMemberId?.let {
            Person.MotorsportRegMetadata(memberId = it)
        }
    )
}