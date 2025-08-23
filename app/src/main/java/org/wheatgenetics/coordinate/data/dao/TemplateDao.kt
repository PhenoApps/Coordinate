package org.wheatgenetics.coordinate.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.wheatgenetics.coordinate.data.model.Template

@Dao
interface TemplateDao {
    @Query("SELECT * FROM templates ORDER BY type ASC")
    suspend fun getAllTemplatesOnce(): List<Template>


    @Query("SELECT * FROM templates ORDER BY type ASC")
    fun getAllTemplates(): LiveData<List<Template>>

    @Query("SELECT * FROM templates WHERE _id = :id")
    suspend fun getTemplateById(id: Long): Template?

    @Query("SELECT * FROM templates WHERE type = :type LIMIT 1")
    suspend fun getTemplateByType(type: Int): Template?

    @Query("SELECT * FROM templates WHERE type = :userDefinedType")
    fun getUserDefinedTemplates(userDefinedType: Int = 2): LiveData<List<Template>>

    @Query("SELECT * FROM templates WHERE title = :title LIMIT 1")
    suspend fun getTemplateByTitle(title: String): Template?

    @Query("SELECT EXISTS(SELECT 1 FROM templates WHERE type = :type)")
    suspend fun existsByType(type: Int): Boolean

    @Insert
    suspend fun insertTemplate(template: Template): Long

    @Update
    suspend fun updateTemplate(template: Template)

    @Delete
    suspend fun deleteTemplate(template: Template)

    @Query("DELETE FROM templates WHERE _id > 2")
    suspend fun deleteUserDefined()

    @Query("DELETE FROM templates")
    suspend fun deleteAll()
}