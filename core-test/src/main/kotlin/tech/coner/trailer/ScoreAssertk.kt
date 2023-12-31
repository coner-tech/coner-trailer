package tech.coner.trailer

import assertk.Assert
import assertk.all
import assertk.assertions.*
import tech.coner.trailer.eventresults.Score
import java.math.BigDecimal

fun Assert<Score>.value() = prop("value") { it.value }
fun Assert<Score>.hasValue(expected: BigDecimal) = value().isEqualTo(expected)
fun Assert<Score>.hasValue(expected: String) = hasValue(BigDecimal(expected))

fun Assert<Score>.penalty() = prop("penalty") { it.penalty }
fun Assert<Score>.hasDidNotFinish() = all {
    penalty().isNotNull().isSameInstanceAs(Score.Penalty.DidNotFinish)
    value().isGreaterThanOrEqualTo(Score.Penalty.DidNotFinish.floor)
}
fun Assert<Score>.hasDisqualified() = all {
    penalty().isNotNull().isSameInstanceAs(Score.Penalty.Disqualified)
    value().isGreaterThanOrEqualTo(Score.Penalty.Disqualified.floor)
}