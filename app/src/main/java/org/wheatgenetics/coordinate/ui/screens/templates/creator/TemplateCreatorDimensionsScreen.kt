package org.wheatgenetics.coordinate.ui.screens.templates.creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.ui.navigation.TemplateCreatorOptionalFieldsRoute
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions
import org.wheatgenetics.coordinate.viewmodel.TemplateCreatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateCreatorDimensionsScreen(
    editTemplateId: Long? = null,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: TemplateCreatorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LocalContext.current

    var templateName by remember { mutableStateOf("") }
    var rows by remember { mutableStateOf("") }
    var columns by remember { mutableStateOf("") }

    // Initialize when the screen loads
    LaunchedEffect(editTemplateId) {
        viewModel.initializeTemplate(editTemplateId)
    }

    // Update local state when template loads (for edit mode)
    LaunchedEffect(state.template) {
        state.template?.let { template ->
            templateName = template.title
            rows = template.rows.toString()
            columns = template.cols.toString()
        }
    }

    // Handle errors
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.paddingLarge),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMedium)
    ) {
        Text(
            text = stringResource(R.string.frag_template_creator_naming_title),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = templateName,
            onValueChange = { templateName = it },
            label = { Text(stringResource(R.string.frag_grid_creator_template_name_et_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = templateName.isBlank() || state.isTemplateNameValid
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMedium)
        ) {
            OutlinedTextField(
                value = rows,
                onValueChange = { rows = it.filter { char -> char.isDigit() } },
                label = { Text(stringResource(R.string.frag_template_creator_row_naming_title)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = rows.toIntOrNull()?.let { it <= 0 } != false
            )

            OutlinedTextField(
                value = columns,
                onValueChange = { columns = it.filter { char -> char.isDigit() } },
                label = { Text(stringResource(R.string.frag_template_creator_column_naming_title)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = columns.toIntOrNull()?.let { it <= 0 } != false
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() }
            ) {
                Text(stringResource(R.string.cancelButtonText))
            }

            Button(
                onClick = {
                    val rowsInt = rows.toIntOrNull() ?: 0
                    val colsInt = columns.toIntOrNull() ?: 0

                    if (state.isTemplateNameValid && rowsInt > 0 && colsInt > 0) {

                        viewModel.updateTemplateDimensions(templateName, rowsInt, colsInt)
                        navController.navigate(
                            TemplateCreatorOptionalFieldsRoute(
                                templateTitle = templateName,
                                editTemplateId = editTemplateId
                            )
                        )
                    }
                },
                enabled = state.isTemplateNameValid &&
                        (rows.toIntOrNull() ?: 0) > 0 &&
                        (columns.toIntOrNull() ?: 0) > 0
            ) {
                Text(stringResource(R.string.frag_grid_creator_one_next_btn))
            }
        }
    }
}

@Preview
@Composable
fun TemplateCreatorDimensionsScreenPreview() {
    CoordinateTheme {
        TemplateCreatorDimensionsScreen(
            navController = rememberNavController(),
            snackbarHostState = SnackbarHostState()
        )
    }
}