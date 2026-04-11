package org.wheatgenetics.coordinate.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.wheatgenetics.coordinate.ui.components.BottomNavigationBar
import org.wheatgenetics.coordinate.ui.components.CoordinateAppBar
import org.wheatgenetics.coordinate.ui.navigation.CollectorRoute
import org.wheatgenetics.coordinate.ui.navigation.GridsRoute
import org.wheatgenetics.coordinate.ui.navigation.ProjectsRoute
import org.wheatgenetics.coordinate.ui.navigation.TemplatesRoute
import org.wheatgenetics.coordinate.ui.screens.collector.CollectorScreen
import org.wheatgenetics.coordinate.ui.screens.grids.GridsScreen
import org.wheatgenetics.coordinate.ui.screens.projects.ProjectsScreen
import org.wheatgenetics.coordinate.ui.screens.templates.TemplatesScreen

@Composable
fun CoordinateApp() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { CoordinateAppBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController) },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GridsRoute(),
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<GridsRoute>{ GridsScreen(navController, snackbarHostState) }
            composable<TemplatesRoute>{ TemplatesScreen(navController, snackbarHostState) }
            composable<ProjectsRoute>{ ProjectsScreen(navController, snackbarHostState) }
            // composable("settings")  { GridsRoute(navController) }

            composable<CollectorRoute>{ backStackEntry ->
                val collectorRoute = backStackEntry.toRoute<CollectorRoute>()
                CollectorScreen(
                    gridId = collectorRoute.gridId,
                    navController = navController
                )
            }
        }
    }
}