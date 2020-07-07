package org.coner.trailer

import java.util.*

data class Person(
        val id: UUID = UUID.randomUUID(),
        val memberId: String,
        val name: String
) {

}
