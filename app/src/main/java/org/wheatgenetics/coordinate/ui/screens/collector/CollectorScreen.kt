package org.wheatgenetics.coordinate.ui.screens.collector

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.wheatgenetics.coordinate.viewmodel.CollectorViewModel

@Composable
fun CollectorScreen(
    gridId: Long,
    navController: NavHostController,
    vm: CollectorViewModel = hiltViewModel()
) {
    val entries by vm.getEntriesForGrid(gridId).observeAsState(emptyList())

    LazyColumn {
        items(
            entries,
            key = { entry -> entry.id ?: 0 }
        ) { entry ->
            ListItem(
                headlineContent = { Text(entry.data ?: "") },
                supportingContent = { Text("Row ${entry.row}, Col ${entry.col}") }
            )
            HorizontalDivider()
        }
    }
}