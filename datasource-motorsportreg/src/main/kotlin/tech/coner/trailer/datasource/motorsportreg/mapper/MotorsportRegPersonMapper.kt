package tech.coner.trailer.datasource.motorsportreg.mapper

import tech.coner.trailer.Person
import tech.coner.trailer.client.motorsportreg.model.Member

class MotorsportRegPersonMapper {
    fun updateCore(core: Person, motorsportRegMember: Member): Person {
        return Person(
                id = core.id,
                clubMemberId = motorsportRegMember.memberId,
                firstName = motorsportRegMember.firstName,
                lastName = motorsportRegMember.lastName,
                motorsportReg = Person.MotorsportRegMetadata(
                        memberId = motorsportRegMember.id
                )
        )
    }

    fun fromMotorsportReg(motorsportRegMember: Member): Person {
        return Person(
                clubMemberId = motorsportRegMember.memberId,
                firstName = motorsportRegMember.firstName,
                lastName = motorsportRegMember.lastName,
                motorsportReg = Person.MotorsportRegMetadata(
                        memberId = motorsportRegMember.id
                )
        )
    }

    fun fromCore(core: Person): Member? {
        return core.motorsportReg?.let { msr -> Member(
                id = msr.memberId,
                firstName = core.firstName,
                lastName = core.lastName,
                memberId = core.clubMemberId
        ) }
    }

}