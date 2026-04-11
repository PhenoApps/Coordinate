package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.ui.components.TemplateCreatorAppBar
import org.wheatgenetics.coordinate.ui.navigation.TemplateCreatorDimensionsRoute
import org.wheatgenetics.coordinate.ui.screens.templates.creator.TemplateCreatorDimensionsScreen
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme

@AndroidEntryPoint
class TemplateCreatorActivity : ComponentActivity() {

    companion object {
        const val EXTRA_EDIT_TEMPLATE_ID = "extra_edit_template_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editTemplateId = intent.getLongExtra(EXTRA_EDIT_TEMPLATE_ID, -1L)
            .takeIf { it != -1L }

        setContent {
            CoordinateTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        TemplateCreatorAppBar(
                            navController = navController,
                            onBackClick = {
                                if (!navController.popBackStack()) {
                                    finish()
                                }
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = TemplateCreatorDimensionsRoute(editTemplateId),
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<TemplateCreatorDimensionsRoute> { backStackEntry ->
                            val route = backStackEntry.toRoute<TemplateCreatorDimensionsRoute>()
                            TemplateCreatorDimensionsScreen(
                                editTemplateId = route.editTemplateId,
                                navController = navController,
                                snackbarHostState = snackbarHostState,
                                // onFinish = { finish() }
                            )
                        }

                        // composable<TemplateCreatorOptionalFieldsRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorOptionalFieldsRoute>()
                        //     TemplateCreatorOptionalFieldsScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState
                        //     )
                        // }
                        //
                        // composable<TemplateCreatorNamingRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorNamingRoute>()
                        //     TemplateCreatorNamingScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState
                        //     )
                        // }
                        //
                        // composable<TemplateCreatorExcludeOptionsRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorExcludeOptionsRoute>()
                        //     TemplateCreatorExcludeOptionsScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState
                        //     )
                        // }
                        //
                        // composable<TemplateCreatorExcludeRandomRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorExcludeRandomRoute>()
                        //     TemplateCreatorExcludeRandomScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState
                        //     )
                        // }
                        //
                        // composable<TemplateCreatorExcludeSelectionRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorExcludeSelectionRoute>()
                        //     TemplateCreatorExcludeSelectionScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState
                        //     )
                        // }
                        //
                        // composable<TemplateCreatorPreviewRoute> { backStackEntry ->
                        //     val route = backStackEntry.toRoute<TemplateCreatorPreviewRoute>()
                        //     TemplateCreatorPreviewScreen(
                        //         templateTitle = route.templateTitle,
                        //         editTemplateId = route.editTemplateId,
                        //         navController = navController,
                        //         snackbarHostState = snackbarHostState,
                        //         onFinish = {
                        //             setResult(RESULT_OK)
                        //             finish()
                        //         }
                        //     )
                        // }
                    }
                }
            }
        }
    }
}