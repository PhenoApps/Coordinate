package org.wheatgenetics.coordinate.data.repository

import androidx.lifecycle.LiveData
import org.wheatgenetics.coordinate.data.dao.GridDao
import org.wheatgenetics.coordinate.data.model.Grid
import org.wheatgenetics.coordinate.data.model.JoinedGridData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GridsRepository @Inject constructor(private val gridDao: GridDao) {
    fun getAllJoinedGrid(): LiveData<List<JoinedGridData>> = gridDao.getAllJoinedGrids()

    suspend fun getJoinedGridById(id: Long): JoinedGridData? = gridDao.getJoinedGridById(id)

    fun getGridsByProjectId(projectId: Long): LiveData<List<Grid>> = gridDao.getGridsByProjectId(projectId)
    fun getGridsByTemplateId(templateId: Long): LiveData<List<Grid>> = gridDao.getGridsByTemplateId(templateId)

    suspend fun getGridCountByProjectId(projectId: Long): Int = gridDao.getGridCountByProjectId(projectId)

    suspend fun exists(id: Long): Boolean = gridDao.exists(id)
    suspend fun hasAnyGrids(): Boolean = gridDao.hasAnyGrids()
    suspend fun hasGridsInProjects(): Boolean = gridDao.hasGridsInProjects()
    suspend fun existsByTemplateId(templateId: Long): Boolean = gridDao.existsByTemplateId(templateId)
    suspend fun existsByProjectId(projectId: Long): Boolean = gridDao.existsByProjectId(projectId)

    suspend fun insertGrid(grid: Grid): Long = gridDao.insertGrid(grid)
    suspend fun updateGrid(grid: Grid) = gridDao.updateGrid(grid)
    suspend fun deleteGrid(grid: Grid) = gridDao.deleteGrid(grid)

    suspend fun deleteByTemplateId(templateId: Long) = gridDao.deleteByTemplateId(templateId)
    suspend fun deleteByProjectId(projectId: Long) = gridDao.deleteByProjectId(projectId)
    suspend fun deleteAll() = gridDao.deleteAll()
}