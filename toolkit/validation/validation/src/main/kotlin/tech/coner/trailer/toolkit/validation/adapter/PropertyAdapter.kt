package tech.coner.trailer.toolkit.validation.adapter

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

interface PropertyAdapter<I, R> {
    operator fun invoke(property: KProperty1<I, *>?): KProperty1<R, *>?
}

internal class PropertyAdapterImpl<I, R> (
    private val map: Map<KProperty1<I, *>?, KProperty1<R, *>?>
) : PropertyAdapter<I, R> {
    override operator fun invoke(property: KProperty1<I, *>?): KProperty1<R, *>? {
        return map[property]
    }
}

inline fun <I, reified R> propertyAdapterOf(
    vararg pairs: Pair<KProperty1<I, *>?, KProperty1<R, *>?>
): PropertyAdapter<I, R> {
    return propertyAdapterOf(rClass = R::class, pairs = pairs)
}

fun <I, R> propertyAdapterOf(
    rClass: KClass<*>,
    vararg pairs: Pair<KProperty1<I, *>?, KProperty1<R, *>?>
): PropertyAdapter<I, R> {
    check(pairs.size == rClass.properties.size + 1) {
        "Count of validated pairs doesn't match"
    }
    return PropertyAdapterImpl(pairs.toMap())
}

private val KClass<*>.properties: List<KProperty1<*, *>>
    get() = members.filterIsInstance<KProperty1<*, *>>()