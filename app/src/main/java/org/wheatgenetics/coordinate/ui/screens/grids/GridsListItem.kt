package org.wheatgenetics.coordinate.ui.screens.grids

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
import androidx.compose.ui.unit.sp
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.data.model.JoinedGridData
import org.wheatgenetics.coordinate.ui.components.ListItemIconButton
import org.wheatgenetics.coordinate.ui.preview.PreviewSampleData
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions
import org.wheatgenetics.coordinate.utils.GridOptionsParser
import org.wheatgenetics.coordinate.utils.TimestampUtil

@Composable
fun GridListItem(
    joinedGrid: JoinedGridData,
    onDelete: () -> Unit,
    onExport: () -> Unit,
    onOpen: () -> Unit,
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
                .height(IntrinsicSize.Min)
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.dimensions.paddingMedium)
            ) {
                // grid title
                Text(
                    text = GridOptionsParser.extractGridName(joinedGrid.grid.options),
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                // project title
                Text(
                    text = joinedGrid.project?.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // timestamp
                GridOptionsParser.extractTimestamp(joinedGrid.grid.options)?.let { timestamp ->
                    Text(
                        text = TimestampUtil.formatTimestampString(timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                    onClick = onExport,
                    drawableRes = R.drawable.ic_export_black,
                    contentDescription = stringResource(R.string.export_dir)
                )

                ListItemIconButton(
                    onClick = onOpen,
                    drawableRes = R.drawable.ic_collect_data_black,
                    contentDescription = "Show grids"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridListItemPreview() {
    CoordinateTheme {
        GridListItem(
            joinedGrid = PreviewSampleData.sampleJoinedGrid,
            onDelete = { },
            onExport = { },
            onOpen = { }
        )
    }
}

@Preview(name = "Dark Theme")
@Composable
fun GridListItemDarkPreview() {
    CoordinateTheme(darkTheme = true) {
        GridListItem(
            joinedGrid = PreviewSampleData.sampleJoinedGrid,
            onDelete = { },
            onExport = { },
            onOpen = { }
        )
    }
}

@Preview(name = "Long Text Dark Theme")
@Composable
fun GridListItemLongDarkPreview() {
    CoordinateTheme(darkTheme = true) {
        GridListItem(
            joinedGrid = PreviewSampleData.longNameJoinedGrid,
            onDelete = { },
            onExport = { },
            onOpen = { }
        )
    }
}