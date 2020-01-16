package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class TemplateSetter extends java.lang.Object
{
    private final android.app.Activity activity;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.app.Activity activity() { return this.activity; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    TemplateSetter(final android.app.Activity activity) { super(); this.activity = activity; }
}