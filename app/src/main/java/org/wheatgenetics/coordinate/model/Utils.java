package org.wheatgenetics.coordinate.model;

@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Utils extends java.lang.Object
{
    @java.lang.SuppressWarnings("DefaultLocale")
    static int valid(final int value, final int minValue)
    {
        if (value < minValue)
            throw new java.lang.IllegalArgumentException(
                java.lang.String.format("value must be >= %d", minValue));
        else
            return value;
    }
}