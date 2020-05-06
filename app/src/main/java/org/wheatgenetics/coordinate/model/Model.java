package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

import java.util.Locale;

public abstract class Model implements Cloneable {
    // region Fields
    @NonNull
    private final StringGetter
            stringGetter;
    @IntRange(from = 1)
    private long id;
    // endregion

    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Model(@NonNull final StringGetter stringGetter) {
        super();
        this.stringGetter = stringGetter;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Model(
            @IntRange(from = 1) final long id,
            @NonNull final StringGetter stringGetter) {
        this(stringGetter);
        this.setId(id);
    }

    // region Public Methods
    public static boolean illegal(final long id) {
        return id < 1;
    }
    // endregion

    public static long valid(final long id,
                             @NonNull final StringGetter stringGetter) {
        if (Model.illegal(id))
            throw new IllegalArgumentException(stringGetter.get(
                    R.string.ModelIdMustBeGreaterThanZero));
        else
            return id;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    StringGetter stringGetter() {
        return this.stringGetter;
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return String.format(Locale.getDefault(),
                "id: %02d", this.getId());
    }
    // endregion

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Model)
            return this.getId() == ((Model) object).getId();
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @IntRange(from = 1)
    public long getId() {
        return this.id;
    }

    public void setId(@IntRange(from = 1) final long id) {
        this.id = Model.valid(                          // throws
                id, this.stringGetter());
    }
    // endregion
}