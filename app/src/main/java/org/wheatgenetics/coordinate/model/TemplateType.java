package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;

public enum TemplateType {
    SEED(0), DNA(1), USERDEFINED(2);

    @IntRange(from = 0, to = 2)
    private final int code;

    @SuppressWarnings({"UnnecessaryEnumModifier"})
    private TemplateType(
            @IntRange(from = 0, to = 2) final int code) {
        this.code = code;
    }

    static TemplateType get(
            @IntRange(from = 0, to = 2) final int code) {
        if (TemplateType.SEED.getCode() == code)
            return TemplateType.SEED;
        else if (TemplateType.DNA.getCode() == code)
            return TemplateType.DNA;
        else if (TemplateType.USERDEFINED.getCode() == code)
            return TemplateType.USERDEFINED;
        else
            throw new IllegalArgumentException();
    }

    // region Public Methods
    @IntRange(from = 0, to = 2)
    public int getCode() {
        return this.code;
    }

    public boolean isDefaultTemplate() {
        @IntRange(from = 0, to = 2) final int code = this.getCode();
        return code == TemplateType.SEED.getCode()
                || code == TemplateType.DNA.getCode();
    }
    // endregion
}