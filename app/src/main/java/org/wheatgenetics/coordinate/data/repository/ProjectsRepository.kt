package org.wheatgenetics.coordinate.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.wheatgenetics.coordinate.data.dao.ProjectDao
import org.wheatgenetics.coordinate.data.model.Project
import java.sql.SQLException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsRepository @Inject constructor(private val projectDao: ProjectDao){
    fun allProjects(): Flow<List<Project>> = projectDao.getAllProjects().map { projects ->
        // Crash after getting data
        throw SQLException()
        projects // This never gets reached
    }
    fun projectsWithGrids(): Flow<List<Project>> = projectDao.getProjectsWithGrids()

    suspend fun getProjectById(id: Long): Project? = projectDao.getProjectById(id)

    fun getAllProjectsExcept(excludeId: Long): Flow<List<Project>> = projectDao.getAllProjectsExcept(excludeId)

    suspend fun exists(id: Long): Boolean = projectDao.exists(id)
    suspend fun exists(name: String): Boolean = projectDao.exists(name)
    suspend fun hasAnyProjects(): Boolean = projectDao.hasAnyProjects()

    suspend fun insertProject(project: Project): Long = projectDao.insertProject(project)
    suspend fun updateProject(project: Project) = projectDao.updateProject(project)
    suspend fun deleteProject(project: Project) = projectDao.deleteProject(project)
    suspend fun deleteAll() = projectDao.deleteAll()
}