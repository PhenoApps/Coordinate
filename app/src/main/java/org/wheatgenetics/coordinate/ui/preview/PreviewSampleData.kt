// ui/preview/SampleData.kt
package org.wheatgenetics.coordinate.ui.preview

import org.wheatgenetics.coordinate.data.model.Grid
import org.wheatgenetics.coordinate.data.model.JoinedGridData
import org.wheatgenetics.coordinate.data.model.Project
import org.wheatgenetics.coordinate.data.model.Template

object PreviewSampleData {

    // sample Projects
    val sampleProject = Project(
        id = 1,
        title = "Sample Project",
        timestamp = System.currentTimeMillis()
    )

    val longTitleProject = Project(
        id = 2,
        title = "Very Long Project Name That Might Overflow In The UI",
        timestamp = System.currentTimeMillis() - 86400000
    )

    val sampleTemplate = Template(
        id = 1,
        title = "Sample Template",
        type = 3,
        rows = 3,
        cols = 2,
        colNumbering = 1,
        rowNumbering = 1,
        options = "",
        excludedCols = "[]",
        excludedRows = "[]",
        excludedCells = "[]",
        timestamp = System.currentTimeMillis()
    )

    val sampleGrid = Grid(
        id = 1,
        projectId = 1,
        templateId = 1,
        options = """[{"field":"Identification","value":"Sample Grid"},{"field":"timestamp","value":"2024-01-15 10:30:00"}]""",
        person = "John Doe",
        timestamp = System.currentTimeMillis()
    )

    val longNameGrid = Grid(
        id = 2,
        projectId = 2,
        templateId = 1,
        options = """[{"field":"Identification","value":"Very Long Grid Name That Should Test Text Overflow Behavior"},{"field":"timestamp","value":"2024-01-14 14:15:30"}]""",
        person = "Jane Smith",
        timestamp = System.currentTimeMillis() - 43200000
    )

    val sampleJoinedGrid = JoinedGridData(
        grid = sampleGrid,
        project = sampleProject,
        template = sampleTemplate
    )

    val longNameJoinedGrid = JoinedGridData(
        grid = longNameGrid,
        project = longTitleProject,
        template = sampleTemplate
    )
}