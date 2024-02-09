package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Class
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.ClassModel

class ClassAbbreviationStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.abbreviation
    }
}

class ClassNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.name
    }
}

class ClassSortStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return model.sort?.toString() ?: ""
    }
}

class ClassPaxedStringFieldAdapter(
) : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class> {
    override fun invoke(model: Class): String {
        return if (model.paxed) "pax" else ""
    }
}

class ClassPaxFactorStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Class> {
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
) : tech.coner.trailer.presentation.library.adapter.Adapter<Class, ClassModel> {
    override fun invoke(model: Class): ClassModel {
        return ClassModel(
            clazz = model,
            adapter = this
        )
    }
}