package org.coner.trailer.datasource.motorsportreg.mapper

import org.coner.trailer.Person
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse

class MotorsportRegPersonMapper {
    fun updateCore(core: Person, motorsportRegMember: GetMembersResponse.Member): Person {
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

    fun fromMotorsportReg(motorsportRegMember: GetMembersResponse.Member): Person {
        return Person(
                clubMemberId = motorsportRegMember.memberId,
                firstName = motorsportRegMember.firstName,
                lastName = motorsportRegMember.lastName,
                motorsportReg = Person.MotorsportRegMetadata(
                        memberId = motorsportRegMember.id
                )
        )
    }

}