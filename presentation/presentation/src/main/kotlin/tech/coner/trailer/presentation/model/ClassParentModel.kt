package tech.coner.trailer.presentation.model

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.adapter.ClassParentModelAdapter

class ClassParentModel(
    private val classParent: Class.Parent,
    private val adapter: ClassParentModelAdapter
) : Model {
    val name
        get() = adapter.nameAdapter(classParent)
}
