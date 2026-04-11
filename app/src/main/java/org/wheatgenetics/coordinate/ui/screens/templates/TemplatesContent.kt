package org.wheatgenetics.coordinate.ui.screens.templates

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
import org.wheatgenetics.coordinate.data.model.Template
import org.wheatgenetics.coordinate.ui.preview.PreviewSampleData
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions

@Composable
fun TemplatesContent(
    templates: List<Template>,
    onCreateTemplate: () -> Unit,
    onCreateGrid: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onExport: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onShowGrids: (Long) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(
                items = templates,
                key = { it.id!! }
            ) { template ->
                TemplateListItem(
                    template = template,
                    onCreateGrid = { template.id?.let { onCreateGrid(it) } },
                    onShowGrids = { template.id?.let { onShowGrids(it) } },
                    onExport = { template.id?.let { onExport(it) } },
                    onEdit = { template.id?.let { onEdit(it) } },
                    onDelete = { template.id?.let { onDelete(it) } }
                )
                HorizontalDivider()
            }
        }
        FloatingActionButton(
            onClick = onCreateTemplate,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.dimensions.paddingLarge),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Template")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TemplatesContentPreview() {
    CoordinateTheme {
        TemplatesContent (
            templates = listOf(PreviewSampleData.sampleTemplate),
            onCreateTemplate = { },
            onCreateGrid = { },
            onDelete = { },
            onExport = { },
            onShowGrids = { },
            onEdit = { }
        )
    }
}