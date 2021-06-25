package org.wheatgenetics.coordinate.gc.vs;

import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

public interface Handler {
    void setPerson(String person);

    void handleSetValuesDone(NonNullOptionalFields optionalFields);
}