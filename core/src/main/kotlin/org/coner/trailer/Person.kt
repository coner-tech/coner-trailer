package org.coner.trailer

import java.util.*

data class Person(
        val id: UUID = UUID.randomUUID(),
        val clubMemberId: String?,
        val firstName: String,
        val lastName: String,
        val motorsportReg: MotorsportRegMetadata? = null
) {

    data class MotorsportRegMetadata(val memberId: String)
}
