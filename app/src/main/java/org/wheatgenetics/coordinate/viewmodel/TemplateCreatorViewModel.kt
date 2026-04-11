package org.wheatgenetics.coordinate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.wheatgenetics.coordinate.data.model.Template
import org.wheatgenetics.coordinate.data.repository.TemplatesRepository
import javax.inject.Inject

data class TemplateCreatorState(
    val isLoading: Boolean = false,
    val template: Template? = null,
    val isEditMode: Boolean = false,
    val error: String? = null,
    val isTemplateNameValid: Boolean = false,
)

@HiltViewModel
class TemplateCreatorViewModel @Inject constructor(
    private val templateRepository: TemplatesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TemplateCreatorState())
    val state: StateFlow<TemplateCreatorState> = _state.asStateFlow()

    fun initializeTemplate(editTemplateId: Long? = null) {
        if (editTemplateId != null) {
            loadTemplateForEdit(editTemplateId)
        } else {
            createNewTemplate()
        }
    }

    private fun loadTemplateForEdit(templateId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val template = templateRepository.getTemplateById(templateId)
                _state.value = _state.value.copy(
                    isLoading = false,
                    template = template,
                    isEditMode = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun createNewTemplate() {
        val newTemplate = Template(
            title = "",
            rows = 0,
            cols = 0,
            colNumbering = 1,
            rowNumbering = 1,
            type = 2,
            generatedExcludedCellsAmount = null,
            excludedCells = "",
            excludedRows = "",
            excludedCols = "",
            entryLabel = "",
            options = "",
            timestamp = null,
        )
        _state.value = _state.value.copy(
            template = newTemplate,
            isEditMode = false
        )
    }

    fun updateTemplateDimensions(title: String, rows: Int, cols: Int) {
        val currentTemplate = _state.value.template ?: return
        val updatedTemplate = currentTemplate.copy(
            title = title,
            rows = rows,
            cols = cols
        )

        viewModelScope.launch {
            try {
                val savedTemplate = if (_state.value.isEditMode) {
                    templateRepository.updateTemplate(updatedTemplate)
                    updatedTemplate
                } else {
                    val id = templateRepository.insertTemplate(updatedTemplate)
                    updatedTemplate.copy(id = id)
                }

                _state.value = _state.value.copy(
                    template = savedTemplate,
                    isEditMode = true // Now it's always edit mode after first save
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun updateTemplateNaming(colNumbering: Int, rowNumbering: Int) {
        val currentTemplate = _state.value.template ?: return
        val updatedTemplate = currentTemplate.copy(
            colNumbering = colNumbering,
            rowNumbering = rowNumbering
        )

        viewModelScope.launch {
            try {
                templateRepository.updateTemplate(updatedTemplate)
                _state.value = _state.value.copy(template = updatedTemplate)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun updateExclusions(excludedCells: Set<Pair<Int, Int>>) {
        val currentTemplate = _state.value.template ?: return

        val excludedCellsJson = if (excludedCells.isNotEmpty()) {
            JSONArray().apply {
                excludedCells.forEach { (row, col) ->
                    put(JSONObject().apply {
                        put("row", row + 1) // Convert from 0-based to 1-based
                        put("col", col + 1)
                    })
                }
            }.toString()
        } else {
            null
        }

        val updatedTemplate = currentTemplate.copy(
            excludedCells = excludedCellsJson,
            generatedExcludedCellsAmount = 0,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            try {
                templateRepository.updateTemplate(updatedTemplate)
                _state.value = _state.value.copy(template = updatedTemplate)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun validateTemplateName(name: String) {
        val currentTemplate = _state.value.template

        // Check if name is blank
        if (name.isBlank()) {
            _state.value = _state.value.copy(isTemplateNameValid = false)
            return
        }

        // If we're editing and the name hasn't changed, it's valid
        if (currentTemplate?.title == name) {
            _state.value = _state.value.copy(isTemplateNameValid = true)
            return
        }

        // Check if name already exists in database
        viewModelScope.launch {
            try {
                val exists = templateRepository.existsByTitle(name)
                _state.value = _state.value.copy(isTemplateNameValid = !exists)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isTemplateNameValid = false)
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun getExcludedCellsFromTemplate(): Set<Pair<Int, Int>> {
        val template = _state.value.template ?: return emptySet()
        val excludedCells = template.excludedCells ?: return emptySet()

        return try {
            val jsonArray = JSONArray(excludedCells)
            val result = mutableSetOf<Pair<Int, Int>>()
            for (i in 0 until jsonArray.length()) {
                val cellObject = jsonArray.getJSONObject(i)
                val row = cellObject.getInt("row")
                val col = cellObject.getInt("col")
                result.add(Pair(row - 1, col - 1)) // Convert to 0-based for UI
            }
            result
        } catch (e: Exception) {
            emptySet()
        }
    }
}