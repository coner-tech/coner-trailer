package tech.coner.trailer.datasource.crispyfish

import tech.coner.trailer.Person
import tech.coner.crispyfish.model.Registration

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