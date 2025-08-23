package org.wheatgenetics.coordinate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wheatgenetics.coordinate.data.dao.EntryDao
import org.wheatgenetics.coordinate.data.dao.GridDao
import org.wheatgenetics.coordinate.data.dao.ProjectDao
import org.wheatgenetics.coordinate.data.dao.TemplateDao
import org.wheatgenetics.coordinate.data.model.Entry
import org.wheatgenetics.coordinate.data.model.Grid
import org.wheatgenetics.coordinate.data.model.Project
import org.wheatgenetics.coordinate.data.model.Template

@Database(
    entities = [Project::class, Template::class, Grid::class, Entry::class],
    version = 2,
    exportSchema = true
)
abstract class CoordinateDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun templateDao(): TemplateDao
    abstract fun gridDao(): GridDao
    abstract fun entryDao(): EntryDao

    companion object {
        const val DATABASE_NAME = "seedtray1.db"
    }
}