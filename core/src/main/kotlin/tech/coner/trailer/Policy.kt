package tech.coner.trailer

import tech.coner.trailer.eventresults.EventResultsType
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
    val topTimesEventResultsMethod: EventResultsType
) {

    sealed class DataSource {
        /**
         * Source data from Crispy Fish
         */
        object CrispyFish : DataSource()

        /**
         * Source data from nothing (not persisted). Dev/test use only.
         */
        object None : DataSource()
    }
}