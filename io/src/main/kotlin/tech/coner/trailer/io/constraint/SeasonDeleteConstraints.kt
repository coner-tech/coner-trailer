package tech.coner.trailer.io.constraint

import tech.coner.trailer.Season

class SeasonDeleteConstraints : Constraint<Season>() {

    override fun assess(candidate: Season) {
        // https://github.com/caeos/coner-trailer/issues/39
    }
}