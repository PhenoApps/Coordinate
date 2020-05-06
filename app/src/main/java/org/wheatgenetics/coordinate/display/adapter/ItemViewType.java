package org.wheatgenetics.coordinate.display.adapter;

import androidx.annotation.IntRange;

enum ItemViewType {
    UNSPECIFIED(0), TOP(1), LEFT(2), DATA(3);

    @IntRange(from = 0, to = 3)
    private final int code;

    @SuppressWarnings({"UnnecessaryEnumModifier"})
    private ItemViewType(
            @IntRange(from = 0, to = 3) final int code) {
        this.code = code;
    }

    static ItemViewType convert(
            @IntRange(from = 0, to = 3) final int code) {
        if (ItemViewType.TOP.getCode() == code)
            return ItemViewType.TOP;
        else if (ItemViewType.LEFT.getCode() == code)
            return ItemViewType.LEFT;
        else if (
                ItemViewType.DATA.getCode() == code)
            return ItemViewType.DATA;
        else
            return ItemViewType.UNSPECIFIED;
    }

    @IntRange(from = 0, to = 3)
    int getCode() {
        return this.code;
    }
}