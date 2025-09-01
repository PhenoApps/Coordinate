package org.wheatgenetics.coordinate.ui.screens.projects

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.wheatgenetics.coordinate.ui.dialogs.CreateProjectDialog
import org.wheatgenetics.coordinate.viewmodel.ProjectsViewModel

@Composable
fun ProjectsScreen(
    navController: NavHostController,
    snackbar: SnackbarHostState,
    projectsViewModel: ProjectsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by projectsViewModel.state.collectAsStateWithLifecycle()

    // export launcher (CreateDocument)
    var exportId by remember { mutableStateOf<Long?>(null) }
    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/zip")
    ) { uri ->
        val id = exportId ?: return@rememberLauncherForActivityResult
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                val out = navController.context.contentResolver.openOutputStream(uri)
                if (out != null) {
                    // projectsViewModel.exportProjectTo(id, out)
                    out.close()
                    scope.launch {
                        snackbar.showSnackbar("Exported project #$id", withDismissAction = true)
                    }
                }
            }
        }
        exportId = null
    }

    LaunchedEffect(uiState.error) { // error loading projects
        uiState.error?.let { errorMessage ->
            Log.d("TAG", "ProjectsScreen: ")
            snackbar.showSnackbar(
                message = errorMessage,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(uiState.dialog.projectCreated) { // close create project dialog
        if (uiState.dialog.projectCreated) {
            projectsViewModel.hideCreateDialog()
        }
    }

    if (uiState.dialog.isVisible) { // show create project dialog
        CreateProjectDialog(
            projectName = uiState.dialog.projectName,
            onProjectNameChange = { projectsViewModel.updateProjectName(it) },
            onDismiss = { projectsViewModel.hideCreateDialog() },
            onConfirm = { projectsViewModel.createProject() },
            errorMessage = uiState.dialog.errorMessage
        )
    }
    ProjectsContent(
        projects = uiState.projects,
        onCreateProject = { projectsViewModel.showCreateDialog() },
        onCreateGrid = { id ->
            navController.navigate("editProject/$id")
        },
        onDelete = { id ->
            projectsViewModel.delete(id)
            scope.launch {
                snackbar.showSnackbar("Deleted project #$id", withDismissAction = true)
            }
        },
        onExport = { id ->
            exportId = id
            exportLauncher.launch("project_$id.zip")
        },
        onViewGrids = { id ->
            navController.navigate("gridsByProject/$id")
        }
    )
}