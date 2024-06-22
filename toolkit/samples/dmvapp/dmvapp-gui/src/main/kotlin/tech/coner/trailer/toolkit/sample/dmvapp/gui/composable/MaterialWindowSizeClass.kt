package tech.coner.trailer.toolkit.sample.dmvapp.gui.composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState

enum class MaterialWindowSizeClass(
    val widthRange: ClosedRange<Int>
) {

    Compact(Int.MIN_VALUE..600),
    Medium(600..839),
    Expanded(840..1199),
    Large(1200..1599),
    ExtraLarge(1600..Int.MAX_VALUE);

    internal val widthRangeDp = widthRange.let { it.start.dp..it.endInclusive.dp }
}

val WindowState.materialSizeClass: MaterialWindowSizeClass
    get() = MaterialWindowSizeClass.entries.first { size.width in it.widthRangeDp }