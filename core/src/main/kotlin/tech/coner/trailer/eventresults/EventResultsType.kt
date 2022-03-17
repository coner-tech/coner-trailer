package tech.coner.trailer.eventresults

import kotlin.reflect.KClass

data class EventResultsType(
        val key: String,
        val title: String,
        val titleShort: String,
        val positionColumnHeading: String,
        val scoreColumnHeading: String,
        val clazz: KClass<out EventResults>
)