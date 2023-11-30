package tech.coner.trailer.app.admin.util

import kotlin.reflect.KProperty

class EnvironmentArgumentBuilder {

    private val items = mutableMapOf<String, String?>()

    var motorsportregOrganizationId by Item("MOTORSPORTREG_ORGANIZATION_ID")
    var motorsportregUsername by Item("MOTORSPORTREG_USERNAME")
    var motorsportregPassword by Item("MOTORSPORTREG_PASSWORD")

    fun build(): Array<String> {
        return items
            .map { "${it.key}=${it.value}" }
            .toTypedArray()
    }

    private inner class Item(
        val name: String,
    ) {
        private var value: String? = null

        operator fun getValue(
            thisRef: EnvironmentArgumentBuilder,
            property: KProperty<*>
        ): String? {
            return value
        }

        operator fun setValue(
            thisRef: EnvironmentArgumentBuilder,
            property: KProperty<*>,
            value: String?
        ) {
            thisRef.items += name to value
        }
    }

}