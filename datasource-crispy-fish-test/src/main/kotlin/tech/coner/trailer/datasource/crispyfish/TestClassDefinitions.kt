package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import java.math.BigDecimal

object TestClassDefinitions {

    object Lscc2019 {
        val BS = ClassDefinition(
            abbreviation = "BS",
            name = "B Street",
            groupName = "Street",
            paxFactor = BigDecimal("0.810"),
            exclude = false,
            paxed = false
        )
        val CS = ClassDefinition(
            abbreviation = "CS",
            name = "C Street",
            groupName = "Street",
            paxFactor = BigDecimal("0.809"),
            exclude = false,
            paxed = false
        )
        val HS = ClassDefinition(
            abbreviation = "HS",
            name = "H Street",
            groupName = "Street",
            paxFactor = BigDecimal("0.780"),
            exclude = false,
            paxed = false
        )
        val NOV = ClassDefinition(
            abbreviation = "NOV",
            name = "Novice",
            groupName = "Novice",
            paxFactor = BigDecimal.ONE,
            exclude = false,
            paxed = true
        )
    }
}
