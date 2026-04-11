package org.wheatgenetics.coordinate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "entries",
    // foreignKeys = [
    //     ForeignKey(
    //         entity = Grid::class,
    //         parentColumns = ["_id"],
    //         childColumns = ["grid"],
    //         onDelete = ForeignKey.CASCADE
    //     )
    // ],
    // indices = [
    //     Index(value = ["grid"]),
    //     Index(value = ["grid", "row", "col"], unique = true)
    // ]
)
data class Entry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long? = null,

    @ColumnInfo(name = "grid")
    val gridId: Long,

    val row: Int,

    val col: Int,

    @ColumnInfo(name = "edata")
    val data: String? = null,

    @ColumnInfo(name = "stamp")
    val timestamp: Long? = null
)