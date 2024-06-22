package tech.coner.trailer.toolkit.sample.dmvapp.gui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import kotlin.reflect.KProperty1

class FocusFirstFocusRequester<MODEL>(
    internal val store: LinkedHashMap<KProperty1<MODEL, *>, Entry>
) {

    fun requestFocus(property: KProperty1<MODEL, *>) {
        if (store.values.any { it.focused }) return
        store[property]?.focusRequester?.requestFocus()
    }

    class Entry(
        val focusRequester: FocusRequester,
    ) {
        var focused: Boolean = false
    }

    companion object {

        fun <MODEL> FocusFirstFocusRequester(properties: List<KProperty1<MODEL, *>>): FocusFirstFocusRequester<MODEL> {
            return FocusFirstFocusRequester(
                store = linkedMapOf(
                    *properties.map { it to Entry(FocusRequester()) }.toTypedArray()
                )
            )
        }
    }
}

@Composable
fun <MODEL> Modifier.focusOnError(
    focusFirstFocusRequester: FocusFirstFocusRequester<MODEL>,
    property: KProperty1<MODEL, *>
): Modifier {
    val focusRequester: FocusRequester = focusFirstFocusRequester.store[property]?.focusRequester ?: return this
    return focusRequester(focusRequester)
        .onFocusEvent {
            focusFirstFocusRequester.store[property]?.focused = it.isFocused
        }
}