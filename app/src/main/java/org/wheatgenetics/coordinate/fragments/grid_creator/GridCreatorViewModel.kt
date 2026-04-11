package org.wheatgenetics.coordinate.fragments.grid_creator

import androidx.lifecycle.ViewModel
import org.wheatgenetics.coordinate.model.TemplateModel
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields

class GridCreatorViewModel : ViewModel() {
    var template: TemplateModel? = null
    var optionalFields: NonNullOptionalFields? = null
}
