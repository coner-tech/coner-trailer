package org.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import java.math.BigDecimal

object TestClassDefinitions {

    object Lscc2019 {
        val CS = ClassDefinition(
                abbreviation = "CS",
                name = "C Street",
                groupName = "Street",
                paxFactor = BigDecimal("0.809"),
                exclude = false,
                paxed = false
        )
    }
}