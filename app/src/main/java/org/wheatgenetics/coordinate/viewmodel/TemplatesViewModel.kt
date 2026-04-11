package org.wheatgenetics.coordinate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.wheatgenetics.coordinate.data.model.Template
import org.wheatgenetics.coordinate.data.repository.TemplatesRepository
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val repo: TemplatesRepository
) : ViewModel() {

    // LiveData straight from DAO/repo
    val templates: LiveData<List<Template>> = repo.allTemplates()

    fun delete(templateId: Long) = viewModelScope.launch {
        val template = repo.getTemplateById(templateId)
        template?.let { repo.deleteTemplate(it) }
    }

    fun createGridFromTemplate(templateId: Long, onGridId: (Long) -> Unit) =
        viewModelScope.launch {
            // val newGridId = repo.createGridFromTemplate(templateId) // implement in repo
            // onGridId(newGridId)
        }

    suspend fun exportTemplateTo(id: Long, out: java.io.OutputStream) {
        // repo.exportTemplate(id, out) // implement in repo (XML or whatever you use)
    }
}