package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.model.ClassModel

class ClassAbbreviationStringFieldAdapter : StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.abbreviation
    }
}

class ClassNameStringFieldAdapter : StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.name
    }
}

class ClassSortStringFieldAdapter : StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.sort?.toString() ?: ""
    }
}

class ClassPaxedStringFieldAdapter(
) : StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return if (model.paxed) "pax" else ""
    }
}

class ClassPaxFactorStringFieldAdapter : StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.paxFactor?.toString() ?: ""
    }
}

class ClassModelAdapter(
    val abbreviationAdapter: ClassAbbreviationStringFieldAdapter,
    val nameAdapter: ClassNameStringFieldAdapter,
    val sortAdapter: ClassSortStringFieldAdapter,
    val paxedAdapter: ClassPaxedStringFieldAdapter,
    val paxFactorAdapter: ClassPaxFactorStringFieldAdapter
) : Adapter<Class, ClassModel> {
    override fun invoke(model: Class): ClassModel {
        return ClassModel(
            clazz = model,
            adapter = this
        )
    }
}