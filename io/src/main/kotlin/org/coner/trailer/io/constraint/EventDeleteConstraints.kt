package org.coner.trailer.io.constraint

import org.coner.trailer.Event

class EventDeleteConstraints : Constraint<Event>() {

    override fun assess(candidate: Event) {
        // TODO: prohibit deletes events in certain cases
        // https://github.com/caeos/coner-trailer/issues/41
    }

}
