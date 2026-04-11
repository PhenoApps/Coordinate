package org.wheatgenetics.coordinate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme
import org.wheatgenetics.coordinate.ui.theme.dimensions

@Composable
fun TemplateCreatorAppBar(
    navController: NavHostController,
    onBackClick: () -> Unit,
) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val title = when {
        // route.contains("TemplateCreatorDimensions") -> stringResource(R.string.template_dimensions_title)
        // route.contains("TemplateCreatorOptionalFields") -> stringResource(R.string.template_optional_fields_title)
        // route.contains("TemplateCreatorNaming") -> stringResource(R.string.template_naming_title)
        // route.contains("TemplateCreatorExcludeOptions") -> stringResource(R.string.template_exclude_options_title)
        // route.contains("TemplateCreatorExcludeRandom") -> stringResource(R.string.template_exclude_random_title)
        // route.contains("TemplateCreatorExcludeSelection") -> stringResource(R.string.template_exclude_selection_title)
        // route.contains("TemplateCreatorPreview") -> stringResource(R.string.template_preview_title)
        else -> stringResource(R.string.new_template_title)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimensions.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                text = title,
                modifier = Modifier.padding(start = MaterialTheme.dimensions.paddingMedium),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun currentTitle(navController: NavHostController): String {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    return when {
        route.contains("GridsRoute") -> stringResource(id = R.string.GridsActivityLabel)
        route.contains("TemplatesRoute") -> stringResource(id = R.string.TemplatesActivityLabel)
        route.contains("ProjectsRoute") -> stringResource(id = R.string.ProjectsActivityLabel)
        route.contains("CollectorRoute") -> stringResource(id = R.string.CollectorActivityLabel)
        else -> stringResource(id = R.string.act_grids_title)
    }
}

@Preview
@Composable
fun TemplateCreatorAppBarPreview() {
    CoordinateTheme {
        TemplateCreatorAppBar(rememberNavController(), onBackClick = { })
    }
}