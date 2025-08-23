package org.wheatgenetics.coordinate.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.wheatgenetics.coordinate.data.model.Project


@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY _id ASC")
    suspend fun getAllProjectsOnce(): List<Project>

    @Query("SELECT * FROM projects ORDER BY _id ASC")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM projects WHERE _id = :id")
    suspend fun getProjectById(id: Long): Project?

    @Query("SELECT * FROM projects WHERE _id != :excludeId")
    fun getAllProjectsExcept(excludeId: Long): LiveData<List<Project>>

    @Query("SELECT * FROM projects WHERE _id IN (SELECT DISTINCT projectId FROM grids WHERE projectId IS NOT NULL AND projectId > 0)")
    fun getProjectsWithGrids(): LiveData<List<Project>>

    @Query("SELECT EXISTS(SELECT 1 FROM projects WHERE _id = :id)")
    suspend fun exists(id: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM projects)")
    suspend fun hasAnyProjects(): Boolean

    @Insert
    suspend fun insertProject(project: Project): Long

    @Update
    suspend fun updateProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("DELETE FROM projects")
    suspend fun deleteAll()
}