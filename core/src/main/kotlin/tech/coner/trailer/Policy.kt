package tech.coner.trailer

import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

data class Policy(
    val id: UUID,
    val club: Club,
    val name: String,
    val conePenaltySeconds: Int,
    val paxTimeStyle: PaxTimeStyle,
    val finalScoreStyle: FinalScoreStyle,
    val authoritativeParticipantDataSource: DataSource,
    val authoritativeRunDataSource: DataSource,
) {

    sealed class DataSource {
        object CrispyFish : DataSource()
    }
}