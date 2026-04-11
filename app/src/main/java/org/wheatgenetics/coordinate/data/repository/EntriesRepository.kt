package org.wheatgenetics.coordinate.data.repository

import androidx.lifecycle.LiveData
import org.wheatgenetics.coordinate.data.dao.EntryDao
import org.wheatgenetics.coordinate.data.model.Entry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntriesRepository @Inject constructor(private val entryDao: EntryDao) {
    fun getEntriesByGridId(gridId: Long): LiveData<List<Entry>> = entryDao.getEntriesByGridId(gridId)

    suspend fun getEntryById(id: Long): Entry? = entryDao.getEntryById(id)
    suspend fun getEntry(gridId: Long, row: Int, col: Int): Entry? = entryDao.getEntry(gridId, row, col)

    suspend fun exists(id: Long): Boolean = entryDao.exists(id)
    suspend fun existsInOtherGrids(excludeGridId: Long, value: String): Boolean = entryDao.existsInOtherGrids(excludeGridId, value)
    suspend fun existsInProjectOtherGrids(excludeGridId: Long, value: String, projectId: Long): Boolean = entryDao.existsInProjectOtherGrids(excludeGridId, value, projectId)
    suspend fun existsOutsideProjects(excludeGridId: Long, value: String): Boolean = entryDao.existsOutsideProjects(excludeGridId, value)
    suspend fun insertEntry(entry: Entry): Long = entryDao.insertEntry(entry)
    suspend fun insertEntries(entries: List<Entry>): List<Long> = entryDao.insertEntries(entries)
    suspend fun updateEntry(entry: Entry) = entryDao.updateEntry(entry)
    suspend fun deleteEntry(entry: Entry) = entryDao.deleteEntry(entry)
    suspend fun deleteByGridId(gridId: Long) = entryDao.deleteByGridId(gridId)
    suspend fun deleteAll() = entryDao.deleteAll()

    suspend fun validateUniqueInAllGrids(excludeGridId: Long, value: String): Boolean =
        !existsInOtherGrids(excludeGridId, value)
    suspend fun validateUniqueInProject(excludeGridId: Long, value: String, projectId: Long): Boolean =
        !existsInProjectOtherGrids(excludeGridId, value, projectId)
}