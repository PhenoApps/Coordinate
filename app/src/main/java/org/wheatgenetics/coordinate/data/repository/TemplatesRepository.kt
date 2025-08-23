package org.wheatgenetics.coordinate.data.repository

import androidx.lifecycle.LiveData
import org.wheatgenetics.coordinate.data.dao.TemplateDao
import org.wheatgenetics.coordinate.data.model.Template
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplatesRepository @Inject constructor(private val templateDao: TemplateDao) {
    fun allTemplates(): LiveData<List<Template>> = templateDao.getAllTemplates()
    fun userDefinedTemplates(): LiveData<List<Template>> = templateDao.getUserDefinedTemplates()

    suspend fun getTemplateById(id: Long): Template? = templateDao.getTemplateById(id)
    suspend fun getTemplateByType(type: Int): Template? = templateDao.getTemplateByType(type)
    suspend fun getTemplateByTitle(title: String): Template? = templateDao.getTemplateByTitle(title)

    suspend fun existsByType(type: Int): Boolean = templateDao.existsByType(type)

    suspend fun insertTemplate(template: Template): Long = templateDao.insertTemplate(template)
    suspend fun updateTemplate(template: Template) = templateDao.updateTemplate(template)
    suspend fun deleteTemplate(template: Template) = templateDao.deleteTemplate(template)
    suspend fun deleteUserDefined() = templateDao.deleteUserDefined()
    suspend fun deleteAll() = templateDao.deleteAll()
}