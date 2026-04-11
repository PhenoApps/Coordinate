package org.wheatgenetics.coordinate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.data.model.Project
import org.wheatgenetics.coordinate.data.repository.ProjectsRepository
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(private val repo: ProjectsRepository) : ViewModel() {

    private val dialog = MutableStateFlow(CreateProjectDialogState())

    val state: StateFlow<ProjectsUiState> =
        repo.allProjects()
            .onStart { emit(emptyList()) }
            .map { projects -> ProjectsUiState(isLoading = false, projects = projects, error = null) }
            .catch { e -> emit(ProjectsUiState(isLoading = false, projects = emptyList(), error = e.message)) }
            .combine(dialog) { ui, d -> ui.copy(dialog = d) }
            .stateIn(
                scope = viewModelScope,
                started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
                initialValue = ProjectsUiState(isLoading = true)
            )

    fun showCreateDialog() {
        dialog.update { it.copy(isVisible = true) }
    }

    fun hideCreateDialog() {
        dialog.update { CreateProjectDialogState() } // reset
    }

    fun updateProjectName(name: String) {
        dialog.update { it.copy(projectName = name.trim(), errorMessage = null) }
    }

    fun createProject() {
        val title = dialog.value.projectName.trim()

        if (title.isEmpty()) {
            dialog.update { it.copy(errorMessage = R.string.CreateProjectAlertDialogEmptyToast) }
            return
        }
        viewModelScope.launch {
            dialog.update { it.copy(isCreating = true, errorMessage = null, projectCreated = false) }

            try {
                if (repo.exists(title)) {
                    dialog.update { it.copy(isCreating = false, errorMessage = R.string.CreateProjectAlertDialogInUseToast) }
                    return@launch
                }

                repo.insertProject(Project(title = title, timestamp = System.currentTimeMillis()))

                dialog.update { it.copy(isCreating = false, projectCreated = true) }

            } catch (_: Exception) {
                dialog.update { it.copy(isCreating = false, errorMessage = R.string.ExcludedRowsOrColsAlertDialogRowLabel) }
            }
        }
    }

    fun delete(projectId: Long) = viewModelScope.launch {
        val project = repo.getProjectById(projectId)
        project?.let { repo.deleteProject(it) }
    }

}

data class ProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val error: String? = null,
    val dialog: CreateProjectDialogState = CreateProjectDialogState()
)

data class CreateProjectDialogState(
    val isVisible: Boolean = false,
    val projectName: String = "",
    val isCreating: Boolean = false,
    val errorMessage: Int? = null,
    val projectCreated: Boolean = false
)