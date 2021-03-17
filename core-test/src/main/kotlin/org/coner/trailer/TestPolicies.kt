package org.coner.trailer

object TestPolicies {

    val lsccV1: Policy by lazy { Policy(
        conePenaltySeconds = 2
    ) }

    val olsccV1: Policy by lazy { Policy(
        conePenaltySeconds = 2
    ) }
}