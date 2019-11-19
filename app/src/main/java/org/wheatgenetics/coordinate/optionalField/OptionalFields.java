package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.support.annotation.NonNull
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class OptionalFields extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>
{
    /** Iterates over non-null optional fields. */
    static class Iterator extends java.lang.Object
    implements java.util.Iterator<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>
    {
        // region Fields
        @android.support.annotation.NonNull @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        final java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>
            arrayList;

        @android.support.annotation.NonNull @android.support.annotation.RestrictTo(
            android.support.annotation.RestrictTo.Scope.SUBCLASSES)
        final java.util.ListIterator<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>
            listIterator;
        // endregion

        Iterator(@android.support.annotation.NonNull final java.util.ArrayList<
        org.wheatgenetics.coordinate.optionalField.BaseOptionalField> arrayList)
        { super(); this.arrayList = arrayList; this.listIterator = this.arrayList.listIterator(); }

        // region java.util.Iterator<> Overridden Methods
        @java.lang.Override public boolean hasNext()
        {
            while (this.listIterator.hasNext())
            {
                final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                    baseOptionalField = this.arrayList.get(this.listIterator.nextIndex());
                if (null == baseOptionalField) this.listIterator.next(); else return true;
            }
            return false;
        }

        @java.lang.Override
        public org.wheatgenetics.coordinate.optionalField.BaseOptionalField next()
        {
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField result;
            do result = this.listIterator.next(); while (null == result);
            return result;
        }

        @java.lang.Override public void remove()
        { throw new java.lang.UnsupportedOperationException(); }
        // endregion
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull @android.annotation.SuppressLint({"RestrictedApi"})
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    final java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>
        arrayList =
            new java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.BaseOptionalField>();

    // region Overridden Methods
    @java.lang.Override @android.support.annotation.NonNull public java.lang.String toString()
    {
        final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder("{");
        {
            boolean firstOptionalField = true;
            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: this)
            {
                if (firstOptionalField) firstOptionalField = false; else stringBuilder.append(", ");
                stringBuilder.append(baseOptionalField.toString());                  // polymorphism
            }
        }
        return stringBuilder.append("}").toString();
    }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (object instanceof org.wheatgenetics.coordinate.optionalField.OptionalFields)
        {
            final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields =
                (org.wheatgenetics.coordinate.optionalField.OptionalFields) object;

            if (this.arrayList.size() != optionalFields.arrayList.size())
                return false;
            else
            {
                {
                    int i = 0;
                    for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                    baseOptionalField: this)
                        if (!baseOptionalField.equals(optionalFields.arrayList.get(i++)))
                            return false;
                }
                return true;
            }
        }
        else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override @android.support.annotation.NonNull
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        return new org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator(
            this.arrayList);
    }
    // endregion
    // endregion
}