package org.wheatgenetics.coordinate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    // icon sizes
    val iconXSmall: Dp = 16.dp,
    val iconSmall: Dp = 20.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val iconXLarge: Dp = 40.dp,

    // button sizes
    val iconButtonSmall: Dp = 40.dp,
    val iconButtonMedium: Dp = 48.dp,
    val iconButtonLarge: Dp = 56.dp,

    // list item specific
    val listItemIconSize: Dp = 24.dp,

    // padding
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 16.dp,
    val listItemPadding: Dp = 6.dp,

    // corner radius
    val cornerRadiusSmall: Dp = 4.dp,
    val cornerRadiusMedium: Dp = 8.dp,
    val cornerRadiusLarge: Dp = 16.dp,
)

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current