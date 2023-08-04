package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.model.ClassParentModel

class ClassParentNameStringFieldAdapter : StringFieldAdapter<Class.Parent> {
    override fun invoke(model: Class.Parent): String {
        return model.name
    }

}

class ClassParentModelAdapter(
    val nameAdapter: ClassParentNameStringFieldAdapter
) : Adapter<Class.Parent, ClassParentModel> {
    override fun invoke(model: Class.Parent): ClassParentModel {
        return ClassParentModel(
            classParent = model,
            adapter = this,
        )
    }
}