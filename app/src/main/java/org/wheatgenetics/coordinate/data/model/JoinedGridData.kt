package org.wheatgenetics.coordinate.data.model

import androidx.room.Embedded

data class JoinedGridData(
    @Embedded val grid: Grid,
    @Embedded(prefix = "project_") val project: Project?,
    @Embedded(prefix = "template_") val template: Template,
)