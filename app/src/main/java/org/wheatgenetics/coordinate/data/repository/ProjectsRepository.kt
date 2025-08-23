package org.wheatgenetics.coordinate.data.repository

import androidx.lifecycle.LiveData
import org.wheatgenetics.coordinate.data.dao.ProjectDao
import org.wheatgenetics.coordinate.data.model.Project
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsRepository @Inject constructor(private val projectDao: ProjectDao){
    fun allProjects(): LiveData<List<Project>> = projectDao.getAllProjects()
    fun projectsWithGrids(): LiveData<List<Project>> = projectDao.getProjectsWithGrids()

    suspend fun getProjectById(id: Long): Project? = projectDao.getProjectById(id)

    fun getAllProjectsExcept(excludeId: Long): LiveData<List<Project>> = projectDao.getAllProjectsExcept(excludeId)

    suspend fun exists(id: Long): Boolean = projectDao.exists(id)
    suspend fun hasAnyProjects(): Boolean = projectDao.hasAnyProjects()

    suspend fun insertProject(project: Project): Long = projectDao.insertProject(project)
    suspend fun updateProject(project: Project) = projectDao.updateProject(project)
    suspend fun deleteProject(project: Project) = projectDao.deleteProject(project)
    suspend fun deleteAll() = projectDao.deleteAll()
}