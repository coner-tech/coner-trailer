package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.ClassParentModel

class ClassParentNameStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class.Parent> {
    override fun invoke(model: Class.Parent): String {
        return model.name
    }

}

class ClassParentModelAdapter(
    val nameAdapter: ClassParentNameStringFieldAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<Class.Parent, ClassParentModel> {
    override fun invoke(model: Class.Parent): ClassParentModel {
        return ClassParentModel(
            classParent = model,
            adapter = this,
        )
    }
}