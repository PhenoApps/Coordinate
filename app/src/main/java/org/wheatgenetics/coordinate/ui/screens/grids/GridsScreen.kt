package org.wheatgenetics.coordinate.ui.screens.grids

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.wheatgenetics.coordinate.ui.navigation.CollectorRoute
import org.wheatgenetics.coordinate.viewmodel.GridsViewModel

@Composable
fun GridsScreen(
    navController: NavHostController,
    snackbar: SnackbarHostState,
    gridsViewModel: GridsViewModel = hiltViewModel()
) {
    val items by gridsViewModel.grids.observeAsState(emptyList())

    GridsContent(
        grids = items,
        onDelete = { id -> gridsViewModel.delete(id) },
        onExport = {},
        onOpen = { id -> navController.navigate(CollectorRoute(id)) },
    )
}