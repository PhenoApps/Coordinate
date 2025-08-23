package org.wheatgenetics.coordinate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "grids",
    // foreignKeys = [
    //     ForeignKey(
    //         entity = Project::class,
    //         parentColumns = ["_id"],
    //         childColumns = ["projectId"],
    //         onDelete = ForeignKey.CASCADE
    //     ),
    //     ForeignKey(
    //         entity = Template::class,
    //         parentColumns = ["_id"],
    //         childColumns = ["temp"],
    //         onDelete = ForeignKey.CASCADE
    //     )
    // ],
    // indices = [
    //     Index(value = ["temp"]),
    //     Index(value = ["projectId"]),
    // ]
)
data class Grid(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long? = null,

    @ColumnInfo(name = "temp")
    val templateId: Long,

    @ColumnInfo(name = "projectId")
    val projectId: Long?,

    val person: String?,

    val activeRow: Int? = null,

    val activeCol: Int? = null,

    val options: String?,

    @ColumnInfo(name = "stamp")
    val timestamp: Long? = null
)