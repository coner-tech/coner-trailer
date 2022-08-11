package tech.coner.trailer

data class EventContext(
    val event: Event,
    val classes: List<Class>,
    val participants: List<Participant>,
    val runs: List<Run>,
    val extendedParameters: EventExtendedParameters
)
