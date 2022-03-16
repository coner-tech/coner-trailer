package org.coner.trailer.eventresults

class OverallEventResults(
        type: EventResultsType,
        runCount: Int,
        val participantResults: List<ParticipantResult>
) : EventResults(type = type, runCount = runCount) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is OverallEventResults) return false
                if (!super.equals(other)) return false

                if (participantResults != other.participantResults) return false

                return true
        }

        override fun hashCode(): Int {
                var result = super.hashCode()
                result = 31 * result + participantResults.hashCode()
                return result
        }
}