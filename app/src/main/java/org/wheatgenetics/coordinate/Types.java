package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.support.annotation.IntDef
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Types extends java.lang.Object
{
    static final int CREATE_TEMPLATE = 10, CREATE_GRID = 20;

    @android.support.annotation.IntDef({
        org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,
        org.wheatgenetics.coordinate.Types.CREATE_GRID    })
    @java.lang.annotation.Documented public @interface RequestCode {}
}