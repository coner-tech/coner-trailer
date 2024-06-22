package tech.coner.trailer.toolkit.sample.dmvapp.gui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

interface ConerThemeColors {
    val Primary: Color
    val OnPrimary: Color
    val PrimaryContainer: Color
    val OnPrimaryContainer: Color
    val Secondary: Color
    val OnSecondary: Color
    val SecondaryContainer: Color
    val OnSecondaryContainer: Color
    val Tertiary: Color
    val OnTertiary: Color
    val TertiaryContainer: Color
    val OnTertiaryContainer: Color
    val Error: Color
    val OnError: Color
    val ErrorContainer: Color
    val OnErrorContainer: Color
    val Background: Color
    val OnBackground: Color
    val Surface: Color
    val OnSurface: Color
    val SurfaceVariant: Color
    val OnSurfaceVariant: Color
    val Outline: Color
    val PrimaryDark: Color
    val OnPrimaryDark: Color
    val PrimaryContainerDark: Color
    val OnPrimaryContainerDark: Color
    val SecondaryDark: Color
    val OnSecondaryDark: Color
    val SecondaryContainerDark: Color
    val OnSecondaryContainerDark: Color
    val TertiaryDark: Color
    val OnTertiaryDark: Color
    val TertiaryContainerDark: Color
    val OnTertiaryContainerDark: Color
    val ErrorDark: Color
    val OnErrorDark: Color
    val ErrorContainerDark: Color
    val OnErrorContainerDark: Color
    val BackgroundDark: Color
    val OnBackgroundDark: Color
    val SurfaceDark: Color
    val OnSurfaceDark: Color
    val SurfaceVariantDark: Color
    val OnSurfaceVariantDark: Color
    val OutlineDark: Color
    
    fun lightColorScheme() = androidx.compose.material3.lightColorScheme(
        primary = Primary,
        onPrimary = OnPrimary,
        primaryContainer = PrimaryContainer,
        onPrimaryContainer = OnPrimaryContainer,
        secondary = Secondary,
        onSecondary = OnSecondary,
        secondaryContainer = SecondaryContainer,
        onSecondaryContainer = OnSecondaryContainer,
        tertiary = Tertiary,
        onTertiary = OnTertiary,
        tertiaryContainer = TertiaryContainer,
        onTertiaryContainer = OnTertiaryContainer,
        error = Error,
        onError = OnError,
        errorContainer = ErrorContainer,
        onErrorContainer = OnErrorContainer,
        background = Background,
        onBackground = OnBackground,
        surface = Surface,
        onSurface = OnSurface,
        surfaceVariant = SurfaceVariant,
        onSurfaceVariant = OnSurfaceVariant,
        outline = Outline
    )
    
    fun darkColorScheme() = darkColorScheme(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        primaryContainer = PrimaryContainerDark,
        onPrimaryContainer = OnPrimaryContainerDark,
        secondary = SecondaryDark,
        onSecondary = OnSecondaryDark,
        secondaryContainer = SecondaryContainerDark,
        onSecondaryContainer = OnSecondaryContainerDark,
        tertiary = TertiaryDark,
        onTertiary = OnTertiaryDark,
        tertiaryContainer = TertiaryContainerDark,
        onTertiaryContainer = OnTertiaryContainerDark,
        error = ErrorDark,
        onError = OnErrorDark,
        errorContainer = ErrorContainerDark,
        onErrorContainer = OnErrorContainerDark,
        background = BackgroundDark,
        onBackground = OnBackgroundDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = SurfaceVariantDark,
        onSurfaceVariant = OnSurfaceVariantDark,
        outline = OutlineDark
    )
}

object ConerThemeColorsGenerated20240628_2 : ConerThemeColors {
    override val Primary = Color(0xFF063E65)
    override val OnPrimary = Color(0xFFFFFFFF)
    override val PrimaryContainer = Color(0xFFA1C9E6)
    override val OnPrimaryContainer = Color(0xFF031F33)
    override val Secondary = Color(0xFFF15A24)
    override val OnSecondary = Color(0xFFFFFFFF)
    override val SecondaryContainer = Color(0xFFE6B8A8)
    override val OnSecondaryContainer = Color(0xFF331308)
    override val Tertiary = Color(0xFF428D00)
    override val OnTertiary = Color(0xFFFFFFFF)
    override val TertiaryContainer = Color(0xFFBFE69D)
    override val OnTertiaryContainer = Color(0xFF183300)
    override val Error = Color(0xFFB3261E)
    override val OnError = Color(0xFFFFFFFF)
    override val ErrorContainer = Color(0xFFE6ACA9)
    override val OnErrorContainer = Color(0xFF330B09)
    override val Background = Color(0xFFfbfcfc)
    override val OnBackground = Color(0xFF313233)
    override val Surface = Color(0xFFfbfcfc)
    override val OnSurface = Color(0xFF313233)
    override val SurfaceVariant = Color(0xFFd8e0e6)
    override val OnSurfaceVariant = Color(0xFF535e66)
    override val Outline = Color(0xFF7c8d99)

    override val PrimaryDark = Color(0xFF85BDE6)
    override val OnPrimaryDark = Color(0xFF052F4C)
    override val PrimaryContainerDark = Color(0xFF063E66)
    override val OnPrimaryContainerDark = Color(0xFFA1C9E6)
    override val SecondaryDark = Color(0xFFE6A58E)
    override val OnSecondaryDark = Color(0xFF4C1C0B)
    override val SecondaryContainerDark = Color(0xFF66260F)
    override val OnSecondaryContainerDark = Color(0xFFE6B8A8)
    override val TertiaryDark = Color(0xFFAFE67F)
    override val OnTertiaryDark = Color(0xFF244C00)
    override val TertiaryContainerDark = Color(0xFF306600)
    override val OnTertiaryContainerDark = Color(0xFFBFE69D)
    override val ErrorDark = Color(0xFFE69490)
    override val OnErrorDark = Color(0xFF4C100D)
    override val ErrorContainerDark = Color(0xFF661511)
    override val OnErrorContainerDark = Color(0xFFE6ACA9)
    override val BackgroundDark = Color(0xFF313233)
    override val OnBackgroundDark = Color(0xFFe2e4e6)
    override val SurfaceDark = Color(0xFF313233)
    override val OnSurfaceDark = Color(0xFFe2e4e6)
    override val SurfaceVariantDark = Color(0xFF535e66)
    override val OnSurfaceVariantDark = Color(0xFFd2dde6)
    override val OutlineDark = Color(0xFF9daab3)
}

object ConerThemeColorsGenerated20240628_1 : ConerThemeColors {
    override val Primary = Color(0xFF063E65)
    override val OnPrimary = Color(0xFFFFFFFF)
    override val PrimaryContainer = Color(0xFFA1C9E6)
    override val OnPrimaryContainer = Color(0xFF031F33)
    override val Secondary = Color(0xFF428D00)
    override val OnSecondary = Color(0xFFFFFFFF)
    override val SecondaryContainer = Color(0xFFBFE69D)
    override val OnSecondaryContainer = Color(0xFF183300)
    override val Tertiary = Color(0xFFF15A24)
    override val OnTertiary = Color(0xFFFFFFFF)
    override val TertiaryContainer = Color(0xFFE6B8A8)
    override val OnTertiaryContainer = Color(0xFF331308)
    override val Error = Color(0xFFB3261E)
    override val OnError = Color(0xFFFFFFFF)
    override val ErrorContainer = Color(0xFFE6ACA9)
    override val OnErrorContainer = Color(0xFF330B09)
    override val Background = Color(0xFFfbfcfc)
    override val OnBackground = Color(0xFF313233)
    override val Surface = Color(0xFFfbfcfc)
    override val OnSurface = Color(0xFF313233)
    override val SurfaceVariant = Color(0xFFd8e0e6)
    override val OnSurfaceVariant = Color(0xFF535e66)
    override val Outline = Color(0xFF7c8d99)

    override val PrimaryDark = Color(0xFF85BDE6)
    override val OnPrimaryDark = Color(0xFF052F4C)
    override val PrimaryContainerDark = Color(0xFF063E66)
    override val OnPrimaryContainerDark = Color(0xFFA1C9E6)
    override val SecondaryDark = Color(0xFFAFE67F)
    override val OnSecondaryDark = Color(0xFF244C00)
    override val SecondaryContainerDark = Color(0xFF306600)
    override val OnSecondaryContainerDark = Color(0xFFBFE69D)
    override val TertiaryDark = Color(0xFFE6A58E)
    override val OnTertiaryDark = Color(0xFF4C1C0B)
    override val TertiaryContainerDark = Color(0xFF66260F)
    override val OnTertiaryContainerDark = Color(0xFFE6B8A8)
    override val ErrorDark = Color(0xFFE69490)
    override val OnErrorDark = Color(0xFF4C100D)
    override val ErrorContainerDark = Color(0xFF661511)
    override val OnErrorContainerDark = Color(0xFFE6ACA9)
    override val BackgroundDark = Color(0xFF313233)
    override val OnBackgroundDark = Color(0xFFe2e4e6)
    override val SurfaceDark = Color(0xFF313233)
    override val OnSurfaceDark = Color(0xFFe2e4e6)
    override val SurfaceVariantDark = Color(0xFF535e66)
    override val OnSurfaceVariantDark = Color(0xFFd2dde6)
    override val OutlineDark = Color(0xFF9daab3)
}

object ConerThemeColorsGenerated20240628_0 : ConerThemeColors {
    override val Primary = Color(0xFF606060)
    override val OnPrimary = Color(0xFFFFFFFF)
    override val PrimaryContainer = Color(0xFFE6E6E6)
    override val OnPrimaryContainer = Color(0xFF333333)
    override val Secondary = Color(0xFF1F679B)
    override val OnSecondary = Color(0xFFFFFFFF)
    override val SecondaryContainer = Color(0xFFABCDE6)
    override val OnSecondaryContainer = Color(0xFF0A2233)
    override val Tertiary = Color(0xFF77D720)
    override val OnTertiary = Color(0xFFFFFFFF)
    override val TertiaryContainer = Color(0xFFC5E6A8)
    override val OnTertiaryContainer = Color(0xFF1C3308)
    override val Error = Color(0xFFB3261E)
    override val OnError = Color(0xFFFFFFFF)
    override val ErrorContainer = Color(0xFFE6ACA9)
    override val OnErrorContainer = Color(0xFF330B09)
    override val Background = Color(0xFFfcfcfc)
    override val OnBackground = Color(0xFF333333)
    override val Surface = Color(0xFFfcfcfc)
    override val OnSurface = Color(0xFF333333)
    override val SurfaceVariant = Color(0xFFe6e6e6)
    override val OnSurfaceVariant = Color(0xFF666666)
    override val Outline = Color(0xFF999999)

    override val PrimaryDark = Color(0xFFE6E6E6)
    override val OnPrimaryDark = Color(0xFF4C4C4C)
    override val PrimaryContainerDark = Color(0xFF666666)
    override val OnPrimaryContainerDark = Color(0xFFE6E6E6)
    override val SecondaryDark = Color(0xFF93C3E6)
    override val OnSecondaryDark = Color(0xFF0F324C)
    override val SecondaryContainerDark = Color(0xFF144366)
    override val OnSecondaryContainerDark = Color(0xFFABCDE6)
    override val TertiaryDark = Color(0xFFB7E68E)
    override val OnTertiaryDark = Color(0xFF2A4C0B)
    override val TertiaryContainerDark = Color(0xFF38660F)
    override val OnTertiaryContainerDark = Color(0xFFC5E6A8)
    override val ErrorDark = Color(0xFFE69490)
    override val OnErrorDark = Color(0xFF4C100D)
    override val ErrorContainerDark = Color(0xFF661511)
    override val OnErrorContainerDark = Color(0xFFE6ACA9)
    override val BackgroundDark = Color(0xFF333333)
    override val OnBackgroundDark = Color(0xFFe6e6e6)
    override val SurfaceDark = Color(0xFF333333)
    override val OnSurfaceDark = Color(0xFFe6e6e6)
    override val SurfaceVariantDark = Color(0xFF666666)
    override val OnSurfaceVariantDark = Color(0xFFe6e6e6)
    override val OutlineDark = Color(0xFFb3b3b3)
}

object ConerThemeColorsGenerated20240627 : ConerThemeColors {

    override val Primary = Color(0xFF1F679B)
    override val OnPrimary = Color(0xFFFFFFFF)
    override val PrimaryContainer = Color(0xFFABCDE6)
    override val OnPrimaryContainer = Color(0xFF0A2233)
    override val Secondary = Color(0xFF77D720)
    override val OnSecondary = Color(0xFFFFFFFF)
    override val SecondaryContainer = Color(0xFFC5E6A8)
    override val OnSecondaryContainer = Color(0xFF1C3308)
    override val Tertiary = Color(0xFFFF7900)
    override val OnTertiary = Color(0xFFFFFFFF)
    override val TertiaryContainer = Color(0xFFE6BF9D)
    override val OnTertiaryContainer = Color(0xFF331800)
    override val Error = Color(0xFFB3261E)
    override val OnError = Color(0xFFFFFFFF)
    override val ErrorContainer = Color(0xFFE6ACA9)
    override val OnErrorContainer = Color(0xFF330B09)
    override val Background = Color(0xFFfbfcfc)
    override val OnBackground = Color(0xFF313233)
    override val Surface = Color(0xFFfbfcfc)
    override val OnSurface = Color(0xFF313233)
    override val SurfaceVariant = Color(0xFFdae1e6)
    override val OnSurfaceVariant = Color(0xFF565f66)
    override val Outline = Color(0xFF818f99)

    override val PrimaryDark = Color(0xFF93C3E6)
    override val OnPrimaryDark = Color(0xFF0F324C)
    override val PrimaryContainerDark = Color(0xFF144366)
    override val OnPrimaryContainerDark = Color(0xFFABCDE6)
    override val SecondaryDark = Color(0xFFB7E68E)
    override val OnSecondaryDark = Color(0xFF2A4C0B)
    override val SecondaryContainerDark = Color(0xFF38660F)
    override val OnSecondaryContainerDark = Color(0xFFC5E6A8)
    override val TertiaryDark = Color(0xFFE6AF7F)
    override val OnTertiaryDark = Color(0xFF4C2400)
    override val TertiaryContainerDark = Color(0xFF663000)
    override val OnTertiaryContainerDark = Color(0xFFE6BF9D)
    override val ErrorDark = Color(0xFFE69490)
    override val OnErrorDark = Color(0xFF4C100D)
    override val ErrorContainerDark = Color(0xFF661511)
    override val OnErrorContainerDark = Color(0xFFE6ACA9)
    override val BackgroundDark = Color(0xFF313233)
    override val OnBackgroundDark = Color(0xFFe3e4e6)
    override val SurfaceDark = Color(0xFF313233)
    override val OnSurfaceDark = Color(0xFFe3e4e6)
    override val SurfaceVariantDark = Color(0xFF565f66)
    override val OnSurfaceVariantDark = Color(0xFFd5dfe6)
    override val OutlineDark = Color(0xFFa0abb3)
}