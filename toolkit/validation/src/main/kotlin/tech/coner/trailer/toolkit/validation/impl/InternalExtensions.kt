package tech.coner.trailer.toolkit.validation.impl

internal fun <K, I> MutableMap<K?, MutableList<I>>.createOrAppend(key: K, item: I) {
    get(key)
        ?.also { it.add(item) }
        ?: mutableListOf(item)
            .also { put(key, it) }
}

internal fun <K, I> MutableMap<K?, MutableList<I>>.createOrAppend(source: Map<K?, List<I>>) {
    source.forEach { (key, keyItems) ->
        get(key)
            ?.also { it.addAll(keyItems) }
            ?: keyItems.toMutableList()
                .also { put(key, it) }
    }
}