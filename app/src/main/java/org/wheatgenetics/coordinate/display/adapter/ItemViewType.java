package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * androidx.annotation.IntRange
 */
enum ItemViewType
{
    UNSPECIFIED(0), TOP(1), LEFT(2), DATA(3);

    @androidx.annotation.IntRange(from = 0, to = 3) private final int code;

    @java.lang.SuppressWarnings({"UnnecessaryEnumModifier"}) private ItemViewType(
    @androidx.annotation.IntRange(from = 0, to = 3) final int code) { this.code = code; }

    static org.wheatgenetics.coordinate.display.adapter.ItemViewType convert(
    @androidx.annotation.IntRange(from = 0, to = 3) final int code)
    {
        if (org.wheatgenetics.coordinate.display.adapter.ItemViewType.TOP.getCode() == code)
            return org.wheatgenetics.coordinate.display.adapter.ItemViewType.TOP;
        else
            if (org.wheatgenetics.coordinate.display.adapter.ItemViewType.LEFT.getCode() == code)
                return org.wheatgenetics.coordinate.display.adapter.ItemViewType.LEFT;
            else
                if (org.wheatgenetics.coordinate.display.adapter.ItemViewType.DATA.getCode() == code)
                    return org.wheatgenetics.coordinate.display.adapter.ItemViewType.DATA;
                else
                    return org.wheatgenetics.coordinate.display.adapter.ItemViewType.UNSPECIFIED;
    }

    @androidx.annotation.IntRange(from = 0, to = 3) int getCode() { return this.code; }
}