package org.wheatgenetics.coordinate.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Main screen routes
 */
@Serializable
data class GridsRoute(val gridId: Long? = null) // null = grid list, non-null = specific grid

@Serializable
object TemplatesRoute

@Serializable
object ProjectsRoute

@Serializable
data class ProjectDetailRoute(val projectId: Long)

@Serializable
data class TemplateDetailRoute(val templateId: Long)

@Serializable
data class GridDetailRoute(val gridId: Long)

/**
 * Filtered grids routes
 */
@Serializable
data class TemplateGridsRoute(val templateId: Long)

@Serializable
data class ProjectGridsRoute(val projectId: Long)

/**
 * Collect screen routes
 */
@Serializable
data class CollectorRoute(val gridId: Long)

/**
 * Create/edit template routes
 */
@Serializable
object CreateTemplateRoute

@Serializable
data class EditTemplateRoute(val templateId: Long)


/**
 * Create grid route
 */
@Serializable
data class CreateGridRoute(
    val templateId: Long,
    val projectId: Long? = null
)