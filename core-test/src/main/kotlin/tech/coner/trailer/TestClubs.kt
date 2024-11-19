package tech.coner.trailer

import tech.coner.trailer.domain.entity.Club

object TestClubs {

    val lscc = Club(
        name = "Local Sports Car Club"
    )

    val invalidForBlankName = lscc.copy(
        name = ""
    )

    val invalidForNameExceedingMaxLength: Sequence<Club> = sequence {
        (Club.NAME_MAX_LENGTH + 1..Club.NAME_MAX_LENGTH.times(10) step 10)
            .map { length ->
                lscc.copy(
                    name = "l".repeat(length)
                )
            }
    }
}
