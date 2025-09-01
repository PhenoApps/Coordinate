package org.wheatgenetics.coordinate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions

@Composable
fun CoordinateAppBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(56.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = currentTitle(navController),
            modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.paddingLarge),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun currentTitle(navController: NavHostController): String {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    return when {
        route.contains("GridsRoute") -> stringResource(id = R.string.GridsActivityLabel)
        route.contains("TemplatesRoute") -> stringResource(id = R.string.TemplatesActivityLabel)
        route.contains("ProjectsRoute") -> stringResource(id = R.string.ProjectsActivityLabel)
        route.contains("CollectorRoute") -> stringResource(id = R.string.CollectorActivityLabel)
        else -> stringResource(id = R.string.act_grids_title)
    }
}

@Preview
@Composable
fun CoordinateAppBarPreview() {
    CoordinateTheme {
        CoordinateAppBar(rememberNavController())
    }
}