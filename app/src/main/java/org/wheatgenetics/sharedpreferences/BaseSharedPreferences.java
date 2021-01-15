package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 * android.content.SharedPreferences.Editor
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.Utils
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class BaseSharedPreferences extends Object
{
    @androidx.annotation.NonNull private final android.content.SharedPreferences sharedPreferences;

    private void setBoolean(@androidx.annotation.NonNull final String key,
    final boolean value)
    {
        if (this.getBoolean(key) != value)
        {
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();
            if (null != editor) { editor.putBoolean(key, value); editor.apply(); }
        }
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @SuppressWarnings({"WeakerAccess"}) protected void validateStringKey(
    @androidx.annotation.NonNull final String key)
    { throw new AssertionError(key + " is not a valid key."); }

    @SuppressWarnings({"WeakerAccess"}) public BaseSharedPreferences(
    @androidx.annotation.NonNull final android.content.SharedPreferences sharedPreferences)
    { super(); this.sharedPreferences = sharedPreferences; }

    // region Public Methods
    // region String Public Methods
    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public String getString(@androidx.annotation.NonNull final String key)
    { return this.sharedPreferences.getString(key,""); }

    @SuppressWarnings({"unused"}) public String getString(
    @androidx.annotation.NonNull final String key, final boolean validateKey)
    {
        if (validateKey) this.validateStringKey(key);
        return this.getString(key);
    }

    @SuppressWarnings({"WeakerAccess"}) public void setString(
    @androidx.annotation.NonNull final String key        ,
    @androidx.annotation.NonNull final String oldValue   ,
                                       String newValue   ,
                                 final boolean          validateKey)
    {
        newValue = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(newValue);
        if (!oldValue.equals(newValue))
        {
            if (validateKey) this.validateStringKey(key);

            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();
            if (null != editor) { editor.putString(key, newValue); editor.apply(); }
        }
    }

    @SuppressWarnings({"unused"})
    public void setString(@androidx.annotation.NonNull final String key     ,
                          @androidx.annotation.NonNull final String oldValue,
                                                       final String newValue)
    { this.setString(key, oldValue, newValue, /* validateKey => */false); }
    // endregion

    // region Integer Public Methods
    @SuppressWarnings({"WeakerAccess"})
    public int getInt(@androidx.annotation.NonNull final String key)
    { return this.sharedPreferences.getInt(key,-1); }

    @SuppressWarnings({"WeakerAccess"}) public void setInt(
    @androidx.annotation.NonNull final String key, final int newInt)
    {
        if (this.getInt(key) != newInt)
        {
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();
            if (null != editor) { editor.putInt(key, newInt); editor.apply(); }
        }
    }
    // endregion

    // region Long Public Methods
    @SuppressWarnings({"WeakerAccess"})
    public long getLong(@androidx.annotation.NonNull final String key)
    { return this.sharedPreferences.getLong(key,-1); }

    @SuppressWarnings({"unused"}) public void setLong(
    @androidx.annotation.NonNull final String key, final long newLong)
    {
        if (this.getLong(key) != newLong)
        {
            final android.content.SharedPreferences.Editor editor = this.sharedPreferences.edit();
            if (null != editor) { editor.putLong(key, newLong); editor.apply(); }
        }
    }
    // endregion

    // region Boolean Public Methods
    @SuppressWarnings({"WeakerAccess"}) public Boolean getBoolean(
    @androidx.annotation.NonNull final String key)
    { return this.sharedPreferences.getBoolean(key,false); }

    @SuppressWarnings({"unused"}) public void setBooleanToTrue(
    @androidx.annotation.NonNull final String key) { this.setBoolean(key, true); }

    @SuppressWarnings({"unused"}) public void setBooleanToFalse(
    @androidx.annotation.NonNull final String key) { this.setBoolean(key, false); }
    // endregion
    // endregion
}