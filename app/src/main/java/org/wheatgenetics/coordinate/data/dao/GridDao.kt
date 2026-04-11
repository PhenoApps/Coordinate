package org.wheatgenetics.coordinate.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.wheatgenetics.coordinate.data.model.Grid
import org.wheatgenetics.coordinate.data.model.JoinedGridData

@Dao
interface GridDao {
    @Query("""
    SELECT g.*, 
           p._id as project__id, p.title as project_title, p.stamp as project_stamp,
           t._id as template__id, t.title as template_title, t.type as template_type,
           t.`rows` as template_rows, t.cols as template_cols,
           t.erand as template_erand, t.ecells as template_ecells,
           t.erows as template_erows, t.ecols as template_ecols,
           t.cnumb as template_cnumb, t.rnumb as template_rnumb,
           t.entryLabel as template_entryLabel, 
           t.options as template_options,
           t.stamp as template_stamp
    FROM grids g
    LEFT JOIN projects p ON g.projectId = p._id
    INNER JOIN templates t ON g.`temp` = t._id
    ORDER BY g._id ASC
""")
    suspend fun getAllJoinedGridsOnce(): List<JoinedGridData>


    @Query("""
        SELECT 
            g.*,
            p._id as project__id, p.title as project_title, p.stamp as project_stamp,
            t._id as template__id, t.title as template_title, t.type as template_type,
            t.`rows` as template_rows, t.`cols` as template_cols,
            t.erand as template_erand, t.ecells as template_ecells,
            t.erows as template_erows, t.ecols as template_ecols,
            t.cnumb as template_cnumb, t.rnumb as template_rnumb,
            t.entryLabel as template_entryLabel, 
            t.options as template_options,
            t.stamp as template_stamp
        FROM grids g
        LEFT JOIN projects p ON g.projectId = p._id
        INNER JOIN templates t ON g.`temp` = t._id
        ORDER BY g._id ASC
    """)
    fun getAllJoinedGrids(): LiveData<List<JoinedGridData>>

    @Query("""
        SELECT 
            g.*, 
            p._id as project__id, p.title as project_title, p.stamp as project_stamp,
            t._id as template__id, t.title as template_title, t.type as template_type,
            t.`rows` as template_rows, t.`cols` as template_cols,
            t.erand as template_erand, t.ecells as template_ecells,
            t.erows as template_erows, t.ecols as template_ecols,
            t.cnumb as template_cnumb, t.rnumb as template_rnumb,
            t.entryLabel as template_entryLabel, 
            t.options as template_options,
            t.stamp as template_stamp
        FROM grids g
        LEFT JOIN projects p ON g.projectId = p._id
        INNER JOIN templates t ON g.`temp` = t._id
        WHERE g._id = :id
    """)
    suspend fun getJoinedGridById(id: Long): JoinedGridData?

    @Query("SELECT * FROM grids WHERE projectId = :projectId")
    fun getGridsByProjectId(projectId: Long): LiveData<List<Grid>>

    @Query("SELECT * FROM grids WHERE `temp` = :templateId")
    fun getGridsByTemplateId(templateId: Long): LiveData<List<Grid>>

    @Query("SELECT COUNT(*) FROM grids WHERE projectId = :projectId")
    suspend fun getGridCountByProjectId(projectId: Long): Int

    @Query("SELECT EXISTS(SELECT 1 FROM grids WHERE _id = :id)")
    suspend fun exists(id: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM grids)")
    suspend fun hasAnyGrids(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM grids WHERE projectId > 0)")
    suspend fun hasGridsInProjects(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM grids WHERE `temp` = :templateId)")
    suspend fun existsByTemplateId(templateId: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM grids WHERE projectId = :projectId)")
    suspend fun existsByProjectId(projectId: Long): Boolean

    @Insert
    suspend fun insertGrid(grid: Grid): Long

    @Update
    suspend fun updateGrid(grid: Grid)

    @Delete
    suspend fun deleteGrid(grid: Grid)

    @Query("DELETE FROM grids WHERE `temp` = :templateId")
    suspend fun deleteByTemplateId(templateId: Long)

    @Query("DELETE FROM grids WHERE projectId = :projectId")
    suspend fun deleteByProjectId(projectId: Long)

    @Query("DELETE FROM grids")
    suspend fun deleteAll()
}