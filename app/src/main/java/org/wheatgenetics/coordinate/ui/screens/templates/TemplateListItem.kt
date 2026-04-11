package org.wheatgenetics.coordinate.ui.screens.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.data.model.Template
import org.wheatgenetics.coordinate.ui.components.ListItemIconButton
import org.wheatgenetics.coordinate.ui.preview.PreviewSampleData
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions
import org.wheatgenetics.coordinate.utils.TimestampUtil

@Composable
fun TemplateListItem(
    template: Template,
    onCreateGrid: () -> Unit,
    onShowGrids: () -> Unit,
    onExport: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.dimensions.paddingMedium)
            ) {
                // title
                Text(
                    text = template.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                // dimensions
                Row {
                    Text(
                        text = "Rows: ${template.rows}", style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = " Cols: ${template.cols}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // timestamp
                template.timestamp?.let {
                    if (it != 0.toLong()) {
                        Text(
                            text = TimestampUtil.formatDate(it),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            }

            // icon buttons
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                ListItemIconButton(
                    onClick = onDelete,
                    drawableRes = R.drawable.ic_delete_black,
                    contentDescription = stringResource(R.string.tutorial_grid_delete_title)
                )

                ListItemIconButton(
                    onClick = onEdit,
                    drawableRes = R.drawable.ic_pencil,
                    contentDescription = "Edit template"
                )

                ListItemIconButton(
                    onClick = onExport,
                    drawableRes = R.drawable.ic_export_black,
                    contentDescription = stringResource(R.string.export_dir)
                )

                ListItemIconButton(
                    onClick = onShowGrids,
                    drawableRes = R.drawable.ic_show_grids_black,
                    contentDescription = "Show grids"
                )

                ListItemIconButton(
                    onClick = onCreateGrid,
                    drawableRes = R.drawable.ic_create_grid_black,
                    contentDescription = stringResource(R.string.menu_grids_new)
                )
            }
        }
    }
}

@Preview
@Composable
fun TemplateListItemPreview() {
    CoordinateTheme {
        TemplateListItem(
            template = PreviewSampleData.sampleTemplate,
            onCreateGrid = { },
            onShowGrids = { },
            onExport = { },
            onEdit = { },
            onDelete = { })
    }
}

@Preview(name = "Dark Theme")
@Composable
fun TemplateListItemDarkPreview() {
    CoordinateTheme(darkTheme = true) {
        TemplateListItem(
            template = PreviewSampleData.sampleTemplate,
            onCreateGrid = { },
            onShowGrids = { },
            onExport = { },
            onEdit = { },
            onDelete = { })
    }
}