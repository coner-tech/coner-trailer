package tech.coner.trailer.toolkit.sample.dmvapp.gui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import org.kodein.di.direct
import org.kodein.di.instance
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings

val LocalStrings = compositionLocalOf<Strings> { di.direct.instance() }

val strings: Strings
@Composable get() = LocalStrings.current