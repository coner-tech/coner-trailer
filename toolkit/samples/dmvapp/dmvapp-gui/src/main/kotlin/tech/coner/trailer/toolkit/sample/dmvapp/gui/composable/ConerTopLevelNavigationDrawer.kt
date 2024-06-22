package tech.coner.trailer.toolkit.sample.dmvapp.gui.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.presentation.presenter.SettingsPresenter
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerBrandColors
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.LocalWindowState
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings

@Composable
fun ConerTopLevelNavigationDrawer(
    drawerState: DrawerState,
    heroTitle: String,
    heroSubtitle: String,
    innerDrawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val settingsPresenter: SettingsPresenter by rememberInstance()
    val materialWindowSizeClass = LocalWindowState.current.materialSizeClass
    val drawerContent: @Composable () -> Unit = {
        ConerTopLevelNavigationDrawerContent(
            heroTitle = heroTitle,
            heroSubtitle = heroSubtitle,
            innerDrawerContent = innerDrawerContent
        )
    }
    when (materialWindowSizeClass) {
        MaterialWindowSizeClass.Compact,
        MaterialWindowSizeClass.Medium -> ModalNavigationDrawer(
            drawerContent = drawerContent,
            drawerState = drawerState,
            content = content
        )
        MaterialWindowSizeClass.Expanded,
        MaterialWindowSizeClass.Large,
        MaterialWindowSizeClass.ExtraLarge -> PermanentNavigationDrawer(
            drawerContent = drawerContent,
            content = content
        )
    }
}

@Composable
private fun ConerTopLevelNavigationDrawerContent(
    heroTitle: String,
    heroSubtitle: String,
    innerDrawerContent: @Composable ColumnScope.() -> Unit
) {
    ModalDrawerSheet {
        ConerTopLevelNavigationDrawerHero(
            title = heroTitle,
            subtitle = heroSubtitle,
        )
        Column(
            modifier = Modifier
                .padding(28.dp)
        ) {
            innerDrawerContent()
        }
    }
}

@Composable
private fun ColumnScope.ConerTopLevelNavigationDrawerHero(
    title: String,
    subtitle: String
) {
    val settingsPresenter: SettingsPresenter by rememberInstance()
    val materialWindowSizeClass = LocalWindowState.current.materialSizeClass
    Box(
        modifier = Modifier
            .background(ConerBrandColors.LogoGray)
            .height(
                when (materialWindowSizeClass) {
                    MaterialWindowSizeClass.Compact -> 64.dp
                    MaterialWindowSizeClass.Medium -> 112.dp
                    MaterialWindowSizeClass.Expanded,
                    MaterialWindowSizeClass.Large,
                    MaterialWindowSizeClass.ExtraLarge -> 152.dp
                }
            )
            .padding(horizontal = 28.dp)
    ) {
        when (materialWindowSizeClass) {
            MaterialWindowSizeClass.Compact -> Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource("coner-icon/coner-icon_48.png"),
                        contentDescription = strings.conerLogoContentDescription,
                    )
                    Spacer(Modifier.weight(1.0f))
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = title,
                            color = ConerBrandColors.LogoWhite,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                        )
                        Text(
                            text = subtitle,
                            color = ConerBrandColors.LogoWhite,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1
                        )
                    }
                }
            }
            MaterialWindowSizeClass.Medium,
            MaterialWindowSizeClass.Expanded,
            MaterialWindowSizeClass.Large,
            MaterialWindowSizeClass.ExtraLarge -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource("coner-logo/coner-logo_128.png"),
                    contentDescription = strings.conerLogoContentDescription,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = title,
                    color = ConerBrandColors.LogoWhite,
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                Text(
                    text = subtitle,
                    color = ConerBrandColors.LogoWhite,
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
@Preview
private fun ConerTopLevelNavigationDrawerPreview() {
    withDI(di) {
        ConerTheme {
            ConerTopLevelNavigationDrawerContent(
                heroTitle = "Hero Title",
                heroSubtitle = "Hero Subtitle",
                innerDrawerContent = {
                    listOf(
                        Icons.Default.AccountBox,
                        Icons.Default.Settings,
                        Icons.Default.Favorite,
                    )
                        .forEachIndexed { index, icon ->
                            NavigationDrawerItem(
                                label = { Text(icon.name) },
                                selected = index == 0,
                                onClick = {},
                                icon = { Icon(icon, contentDescription = icon.name) },
                            )
                        }
                }
            )
        }
    }
}