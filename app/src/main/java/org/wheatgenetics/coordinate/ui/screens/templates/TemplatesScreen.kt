package org.wheatgenetics.coordinate.ui.screens.templates

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.wheatgenetics.coordinate.viewmodel.TemplatesViewModel

@Composable
fun TemplatesScreen(
    navController: NavHostController,
    snackbar: SnackbarHostState,
    templatesViewModel: TemplatesViewModel = hiltViewModel()
) {
    val templates by templatesViewModel.templates.observeAsState(emptyList())
    val scope = rememberCoroutineScope()

    // Export launcher (CreateDocument)
    var exportId by remember { mutableStateOf<Long?>(null) }
    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/xml")
    ) { uri ->
        val id = exportId ?: return@rememberLauncherForActivityResult
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                val out = navController.context.contentResolver.openOutputStream(uri)
                if (out != null) {
                    templatesViewModel.exportTemplateTo(id, out)
                    out.close()
                    scope.launch {
                        snackbar.showSnackbar("Exported template #$id", withDismissAction = true)
                    }
                }
            }
        }
        exportId = null
    }

    TemplatesContent(
        templates = templates,
        onCreateGrid = { templateId ->
            templatesViewModel.createGridFromTemplate(templateId) { newGridId ->
                // Go straight to collector
                navController.navigate("collector/$newGridId")
            }
        },
        onDelete = { id ->
            templatesViewModel.delete(id)
            scope.launch {
                snackbar.showSnackbar("Deleted template #$id", withDismissAction = true)
            }
        },
        onExport = { id ->
            exportId = id
            exportLauncher.launch("template_$id.xml")
        },
        onEdit = { id ->
            // Navigate to edit screen or show edit dialog
            navController.navigate("editTemplate/$id")
        },
        onShowGrids = { id ->
            // Navigate to a "grids filtered by template" destination you define
            navController.navigate("gridsByTemplate/$id")
        }
    )
}