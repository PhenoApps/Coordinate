package org.wheatgenetics.coordinate.ui.screens.projects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.wheatgenetics.coordinate.data.model.Project
import org.wheatgenetics.coordinate.ui.preview.PreviewSampleData
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions

@Composable
fun ProjectsContent(
    projects: List<Project>,
    onCreateProject: () -> Unit,
    onDelete: (Long) -> Unit,
    onExport: (Long) -> Unit,
    onViewGrids: (Long) -> Unit,
    onCreateGrid: (Long) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(
                items = projects,
                key = { it.id!! }
            ) { project ->
                ProjectListItem(
                    project = project,
                    onDelete = { project.id?.let { onDelete(it) } },
                    onExport = { project.id?.let { onExport(it) } },
                    onViewGrids = { project.id?.let { onViewGrids(it) } },
                    onCreateGrid = { project.id?.let { onCreateGrid(it) } },
                )
                HorizontalDivider()
            }
        }

        FloatingActionButton(
            onClick = onCreateProject,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.dimensions.paddingLarge),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Project")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectsContentPreview() {
    CoordinateTheme {
        ProjectsContent(
            projects = listOf(PreviewSampleData.sampleProject),
            onCreateGrid = { },
            onCreateProject = { },
            onDelete = { },
            onExport = { },
            onViewGrids = { },
        )
    }
}