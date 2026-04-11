package org.wheatgenetics.coordinate.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class TemplateCreatorDimensionsRoute(
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorOptionalFieldsRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorNamingRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorExcludeOptionsRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorExcludeRandomRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorExcludeSelectionRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)

@Serializable
data class TemplateCreatorPreviewRoute(
    val templateTitle: String,
    val editTemplateId: Long? = null
)