package tech.coner.trailer.presentation.constraint

import tech.coner.trailer.io.constraint.CompositeConstraint
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.state.EventRunLatestState

class EventRunLatestConstraints : CompositeConstraint<EventRunLatestState>() {

    val countMustBeGreaterThanZero = propertyConstraint(
        property = EventRunLatestState::count,
        assessFn = { it.count > 0 },
        violationMessageFn = { Strings.constraintsEventRunLatestCountMustBeGreaterThanZero }
    )
}