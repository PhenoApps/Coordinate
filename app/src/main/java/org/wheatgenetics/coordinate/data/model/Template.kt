package org.wheatgenetics.coordinate.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class Template(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long? = null,

    val title: String,

    val type: Int,

    val rows: Int,

    val cols: Int,

    @ColumnInfo(name = "erand")
    val generatedExcludedCellsAmount: Int? = null,

    @ColumnInfo(name = "ecells")
    val excludedCells: String?,

    @ColumnInfo(name = "erows")
    val excludedRows: String?,

    @ColumnInfo(name = "ecols")
    val excludedCols: String?,

    @ColumnInfo(name = "cnumb")
    val colNumbering: Int,

    @ColumnInfo(name = "rnumb")
    val rowNumbering: Int,

    @ColumnInfo(name = "entryLabel")
    val entryLabel: String? = null,

    @ColumnInfo(name = "options")
    val options: String?,

    @ColumnInfo(name = "stamp")
    val timestamp: Long? = null
)