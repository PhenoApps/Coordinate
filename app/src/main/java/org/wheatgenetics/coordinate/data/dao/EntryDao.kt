package org.wheatgenetics.coordinate.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.wheatgenetics.coordinate.data.model.Entry

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY _id ASC")
    suspend fun getAllEntriesOnce(): List<Entry>


    @Query("SELECT * FROM entries WHERE grid = :gridId ORDER BY `row` ASC, `col` ASC")
    fun getEntriesByGridId(gridId: Long): LiveData<List<Entry>>

    // Single item queries remain suspend
    @Query("SELECT * FROM entries WHERE _id = :id")
    suspend fun getEntryById(id: Long): Entry?

    @Query("SELECT * FROM entries WHERE grid = :gridId AND `row` = :row AND `col` = :col LIMIT 1")
    suspend fun getEntry(gridId: Long, row: Int, col: Int): Entry?

    @Query("SELECT EXISTS(SELECT 1 FROM entries WHERE _id = :id)")
    suspend fun exists(id: Long): Boolean

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM entries e 
            INNER JOIN grids g ON e.grid = g._id 
            WHERE e.grid != :excludeGridId 
            AND e.edata = :value
        )
    """)
    suspend fun existsInOtherGrids(excludeGridId: Long, value: String): Boolean

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM entries e 
            INNER JOIN grids g ON e.grid = g._id 
            WHERE e.grid != :excludeGridId 
            AND e.edata = :value 
            AND g.projectId = :projectId
        )
    """)
    suspend fun existsInProjectOtherGrids(excludeGridId: Long, value: String, projectId: Long): Boolean

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM entries e 
            INNER JOIN grids g ON e.grid = g._id 
            WHERE e.grid != :excludeGridId 
            AND e.edata = :value 
            AND (g.projectId IS NULL OR g.projectId <= 0)
        )
    """)
    suspend fun existsOutsideProjects(excludeGridId: Long, value: String): Boolean

    @Insert
    suspend fun insertEntry(entry: Entry): Long

    @Insert
    suspend fun insertEntries(entries: List<Entry>): List<Long>

    @Update
    suspend fun updateEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("DELETE FROM entries WHERE grid = :gridId")
    suspend fun deleteByGridId(gridId: Long)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}