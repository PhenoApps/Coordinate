package org.wheatgenetics.coordinate.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.wheatgenetics.coordinate.ui.theme.dimensions

@Composable
fun ListItemIconButton(
    onClick: () -> Unit,
    @DrawableRes drawableRes: Int,
    contentDescription: String?
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(MaterialTheme.dimensions.iconButtonMedium)
    ) {
        Icon(
            painter = painterResource(id = drawableRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(MaterialTheme.dimensions.listItemIconSize)
        )
    }
}