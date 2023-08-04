package tech.coner.trailer.presentation.model

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.adapter.ClassModelAdapter

class ClassModel(
    private val clazz: Class,
    private val adapter: ClassModelAdapter
) : Model {
    val abbreviation
        get() = adapter.abbreviationAdapter(clazz)
    val name
        get() = adapter.nameAdapter(clazz)
    val sort
        get() = adapter.sortAdapter(clazz)
    val paxed
        get() = adapter.paxedAdapter(clazz)
    val paxFactor
        get() = adapter.paxFactorAdapter(clazz)
}