package org.wheatgenetics.coordinate.gc.vs;

/**
 * Uses:
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
@java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
{
    public abstract void setPerson          (java.lang.String person);
    public abstract void handleSetValuesDone(
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields);
}