package org.wheatgenetics.coordinate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = Color.White,
    primaryContainer = ColorPrimaryLight,
    onPrimaryContainer = Color.Black,
    secondary = ColorAccent,
    onSecondary = Color.White,
    background = SurfaceLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
)

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    onPrimary = Color.Black,
    primaryContainer = ColorPrimaryLight,
    onPrimaryContainer = Color.White,
    secondary = ColorAccent,
    onSecondary = Color.Black,
    background = SurfaceDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
)

@Composable
fun CoordinateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalDimensions provides Dimensions()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}