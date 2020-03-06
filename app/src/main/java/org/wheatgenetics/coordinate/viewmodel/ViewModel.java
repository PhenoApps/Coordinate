package org.wheatgenetics.coordinate.viewmodel;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.lifecycle.ViewModel
 */
public abstract class ViewModel extends androidx.lifecycle.ViewModel
{
    @androidx.annotation.IntRange(from = 1) private long id;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void setId(@androidx.annotation.IntRange(from = 1) final long id) { this.id = id; }

    @androidx.annotation.IntRange(from = 1) public long getId() { return this.id; }
}