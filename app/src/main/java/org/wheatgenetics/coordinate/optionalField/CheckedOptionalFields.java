package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CheckedOptionalFields extends OptionalFields {
    public CheckedOptionalFields(@Nullable final OptionalFields optionalFields) {
        super();
        if (null != optionalFields) this.arrayList.addAll(optionalFields.arrayList);
    }

    @Override
    @NonNull
    public OptionalFields.Iterator iterator() {
        // Only iterates over checked optional fields.
        class Iterator extends OptionalFields.Iterator {
            private Iterator(@NonNull final ArrayList<
                    BaseOptionalField> arrayList) {
                super(arrayList);
            }

            // region Overridden Methods
            @Override
            public boolean hasNext() {
                while (super.hasNext()) {
                    final BaseOptionalField
                            baseOptionalField = this.arrayList.get(this.listIterator.nextIndex());
                    if (baseOptionalField.getChecked()) return true;
                    else super.next();
                }
                return false;
            }

            @Override
            public BaseOptionalField next() {
                BaseOptionalField result;
                do result = super.next(); while (!result.getChecked());
                return result;
            }
            // endregion
        }
        return new Iterator(this.arrayList);
    }

    @IntRange(from = 0)
    public int size() {
        @IntRange(from = 0) int result = 0;

        // noinspection UnusedParameters
        for (final BaseOptionalField baseOptionalField :
                this)
            result++;

        return result;
    }
}