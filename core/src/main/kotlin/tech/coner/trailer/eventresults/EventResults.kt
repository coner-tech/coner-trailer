package tech.coner.trailer.eventresults

abstract class EventResults(
        val type: EventResultsType,
        val runCount: Int
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is EventResults) return false

                if (type != other.type) return false
                if (runCount != other.runCount) return false

                return true
        }

        override fun hashCode(): Int {
                var result = type.hashCode()
                result = 31 * result + runCount
                return result
        }
}