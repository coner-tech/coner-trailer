package tech.coner.trailer

import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

object TestPolicies {

    val lsccV1: Policy by lazy { Policy(
        id = UUID.randomUUID(),
        club = TestClubs.lscc,
        name = "LSCC Archival v1",
        conePenaltySeconds = 2,
        paxTimeStyle = PaxTimeStyle.LEGACY_BUGGED,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS,
        authoritativeParticipantDataSource = Policy.DataSource.CrispyFish,
        authoritativeRunDataSource = Policy.DataSource.CrispyFish
    ) }

    val lsccV2: Policy by lazy { Policy(
        id = UUID.randomUUID(),
        club = TestClubs.lscc,
        name = "LSCC Policy v2",
        conePenaltySeconds = 2,
        paxTimeStyle = PaxTimeStyle.FAIR,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS,
        authoritativeParticipantDataSource = Policy.DataSource.CrispyFish,
        authoritativeRunDataSource = Policy.DataSource.CrispyFish
    ) }

}