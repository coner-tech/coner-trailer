package org.coner.trailer

import java.util.*

data class Person(
        val id: UUID = UUID.randomUUID(),
        val memberId: String?,
        val firstName: String,
        val lastName: String,
        val motorsportRegMetadata: MotorsportRegMetadata? = null
) {

    data class MotorsportRegMetadata(val id: String)
}
