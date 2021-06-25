package org.wheatgenetics.coordinate.optionalField;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.ListIterator;

abstract class OptionalFields implements Iterable<BaseOptionalField> {
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    @SuppressLint({"RestrictedApi"})
    @SuppressWarnings({"Convert2Diamond"})
    final ArrayList<BaseOptionalField>
            arrayList =
            new ArrayList<BaseOptionalField>();

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("{");
        {
            boolean firstOptionalField = true;
            for (final BaseOptionalField
                    baseOptionalField : this) {
                if (firstOptionalField) firstOptionalField = false;
                else stringBuilder.append(", ");
                stringBuilder.append(baseOptionalField.toString());                  // polymorphism
            }
        }
        return stringBuilder.append("}").toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof OptionalFields) {
            final OptionalFields optionalFields =
                    (OptionalFields) object;

            if (this.arrayList.size() != optionalFields.arrayList.size())
                return false;
            else {
                {
                    int i = 0;
                    for (final BaseOptionalField
                            baseOptionalField : this)
                        if (!baseOptionalField.equals(optionalFields.arrayList.get(i++)))
                            return false;
                }
                return true;
            }
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    // region java.lang.Iterable<> Overridden Method
    @Override
    @NonNull
    public OptionalFields.Iterator iterator() {
        return new OptionalFields.Iterator(
                this.arrayList);
    }

    /**
     * Iterates over non-null optional fields.
     */
    static class Iterator extends Object
            implements java.util.Iterator<BaseOptionalField> {
        // region Fields
        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @NonNull
        final ArrayList<BaseOptionalField>
                arrayList;

        @RestrictTo(RestrictTo.Scope.SUBCLASSES)
        @NonNull
        final ListIterator<BaseOptionalField>
                listIterator;
        // endregion

        Iterator(@NonNull final ArrayList<
                BaseOptionalField> arrayList) {
            super();
            this.arrayList = arrayList;
            this.listIterator = this.arrayList.listIterator();
        }

        // region java.util.Iterator<> Overridden Methods
        @Override
        public boolean hasNext() {
            while (this.listIterator.hasNext()) {
                final BaseOptionalField
                        baseOptionalField = this.arrayList.get(this.listIterator.nextIndex());
                if (null == baseOptionalField) this.listIterator.next();
                else return true;
            }
            return false;
        }

        @Override
        public BaseOptionalField next() {
            BaseOptionalField result;
            do result = this.listIterator.next(); while (null == result);
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        // endregion
    }
    // endregion
    // endregion
}