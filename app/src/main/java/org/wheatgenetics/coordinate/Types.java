package org.wheatgenetics.coordinate;

import androidx.annotation.IntDef;

import java.lang.annotation.Documented;

public class Types {
    public static final String UNIQUENESS_BUNDLE_KEY = "uniquenessPreferenceWasClicked";

    public static final int CREATE_TEMPLATE = 10, CREATE_GRID = 20, UNIQUENESS_CLICKED = 30;

    @IntDef({
            Types.CREATE_TEMPLATE,
            Types.CREATE_GRID,
            Types.UNIQUENESS_CLICKED})
    @Documented
    public @interface RequestCode {
    }
}