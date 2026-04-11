package org.wheatgenetics.coordinate.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.ui.navigation.GridsRoute
import org.wheatgenetics.coordinate.ui.navigation.ProjectsRoute
import org.wheatgenetics.coordinate.ui.navigation.TemplatesRoute

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavBarItem(GridsRoute(),  R.string.act_grids_title, R.drawable.ic_dots_grid),
        BottomNavBarItem(TemplatesRoute, R.string.TemplatesActivityLabel, R.drawable.ic_templates),
        BottomNavBarItem(ProjectsRoute, R.string.ProjectsActivityLabel, R.drawable.ic_clipboard_list),
        // BottomItem("settings",  R.string.preference_activity_label,  Icons.Default.Settings)
    )
    NavigationBar(
        modifier = Modifier.systemBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        items.forEach { item ->
            NavigationBarItem(
                selected = isRouteSelected(currentDestination?.route, item.route),
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        restoreState = true
                    }
                },
                icon = { Icon(painter = painterResource(item.icon), contentDescription = null) },
                label = { Text(text = item.labelString()) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    }
}

private fun isRouteSelected(currentRoute: String?, bottomNavRoute: Any): Boolean {
    val routeName = when (bottomNavRoute) {
        is GridsRoute -> "GridsRoute"
        TemplatesRoute -> "TemplatesRoute"
        ProjectsRoute -> "ProjectsRoute"
        else -> return false
    }

    return currentRoute?.contains(routeName) == true
}

private data class BottomNavBarItem(
    val route: Any,
    val titleRes: Int,
    @DrawableRes val icon: Int
) {
    @Composable fun labelString() = stringResource(id = titleRes)
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    MaterialTheme {
        BottomNavigationBar(rememberNavController())
    }
}