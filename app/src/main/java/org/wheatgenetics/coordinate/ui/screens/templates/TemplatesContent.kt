package org.wheatgenetics.coordinate.ui.screens.templates

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.wheatgenetics.coordinate.data.model.Template
import org.wheatgenetics.coordinate.ui.preview.PreviewSampleData
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme

@Composable
fun TemplatesContent(
    templates: List<Template>,
    onCreateGrid: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onExport: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onShowGrids: (Long) -> Unit,
) {
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
}

@Preview(showBackground = true)
@Composable
fun TemplatesContentPreview() {
    CoordinateTheme {
        TemplatesContent (
            templates = listOf(PreviewSampleData.sampleTemplate),
            onCreateGrid = { },
            onDelete = { },
            onExport = { },
            onShowGrids = { },
            onEdit = { }
        )
    }
}