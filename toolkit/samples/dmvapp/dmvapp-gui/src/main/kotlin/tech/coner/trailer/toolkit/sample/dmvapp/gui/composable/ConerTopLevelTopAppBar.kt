package tech.coner.trailer.toolkit.sample.dmvapp.gui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.LocalWindowState

@Composable
fun ConerTopLevelTopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit)
) {
    val materialWindowSizeClass = LocalWindowState.current.materialSizeClass
    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
    when(materialWindowSizeClass) {
        MaterialWindowSizeClass.Compact -> TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            colors = colors,
            actions = actions
        )
        MaterialWindowSizeClass.Medium -> MediumTopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            colors = colors,
            actions = actions
        )
        MaterialWindowSizeClass.Expanded,
        MaterialWindowSizeClass.Large,
        MaterialWindowSizeClass.ExtraLarge -> LargeTopAppBar(
            title = title,
            // navigationIcon omitted -- this size class uses a standard navigation drawer (always open)
            colors = colors,
            actions = actions
        )
    }
}
