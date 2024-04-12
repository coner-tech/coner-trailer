package tech.coner.trailer.io.constraint

import tech.coner.trailer.Event
import tech.coner.trailer.toolkit.konstraints.Constraint

class EventDeleteConstraints : Constraint<Event>() {

    override fun assess(candidate: Event) {
        // TODO: prohibit deletes events in certain cases
        // https://github.com/caeos/coner-trailer/issues/41
    }

}
