package org.wheatgenetics.coordinate.ui.screens.grids

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import org.wheatgenetics.coordinate.data.model.JoinedGridData

@Composable
fun GridsContent(
    grids: List<JoinedGridData>,
    onDelete: (Long) -> Unit,
    onExport: () -> Unit,
    onOpen: (Long) -> Unit,
) {
    LazyColumn {
        items(
            items = grids,
            key = { it.grid.id!! }
        ) { joinedGrid ->
            GridListItem(
                joinedGrid = joinedGrid,
                onDelete = { joinedGrid.grid.id?.let { onDelete(it) } },
                onExport = { onExport },
                onOpen = { joinedGrid.grid.id?.let { onOpen(it) } },
            )
            HorizontalDivider()
        }
    }
}