package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 */
public enum TemplateType
{
    SEED(0), DNA(1), USERDEFINED(2);

    @android.support.annotation.IntRange(from = 0, to = 2) private final int code;

    @java.lang.SuppressWarnings({"UnnecessaryEnumModifier"}) private TemplateType(
    @android.support.annotation.IntRange(from = 0, to = 2) final int code) { this.code = code; }

    static org.wheatgenetics.coordinate.model.TemplateType get(
    @android.support.annotation.IntRange(from = 0, to = 2) final int code)
    {
        if (org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode() == code)
            return org.wheatgenetics.coordinate.model.TemplateType.SEED;
        else
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode() == code)
                return org.wheatgenetics.coordinate.model.TemplateType.DNA;
            else
                if (org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED.getCode() == code)
                    return org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
                else
                    throw new java.lang.IllegalArgumentException();
    }

    // region Public Methods
    @android.support.annotation.IntRange(from = 0, to = 2) public int getCode()
    { return this.code; }

    public boolean isDefaultTemplate()
    {
        @android.support.annotation.IntRange(from = 0, to = 2) final int code = this.getCode();
        return code == org.wheatgenetics.coordinate.model.TemplateType.SEED.getCode()
            || code == org.wheatgenetics.coordinate.model.TemplateType.DNA.getCode ();
    }
    // endregion
}