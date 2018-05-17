package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.IntDef
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Types extends java.lang.Object
{
    private static final int CREATE_TEMPLATE = 10, IMPORT_TEMPLATE = 20, CREATE_GRID = 30;

    @android.support.annotation.IntDef({
        org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,
        org.wheatgenetics.coordinate.Types.IMPORT_TEMPLATE,
        org.wheatgenetics.coordinate.Types.CREATE_GRID    })
    @java.lang.annotation.Documented public @interface RequestCode {}
}