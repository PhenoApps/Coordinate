package org.wheatgenetics.coordinate;

/**
 * Uses:
 * androidx.annotation.IntDef
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Types extends java.lang.Object
{
    public static final java.lang.String UNIQUENESS_BUNDLE_KEY = "uniquenessPreferenceWasClicked";

    public static final int CREATE_TEMPLATE = 10;
           static final int CREATE_GRID     = 20, UNIQUENESS_CLICKED = 30;

    @androidx.annotation.IntDef({
        org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE   ,
        org.wheatgenetics.coordinate.Types.CREATE_GRID       ,
        org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED})
    @java.lang.annotation.Documented public @interface RequestCode {}
}