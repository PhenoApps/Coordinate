package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class CurrentGridUniqueJoinedGridModels
        extends BaseJoinedGridModels
        implements Iterable<CurrentGridUniqueJoinedGridModel> {
    private ArrayList<CurrentGridUniqueJoinedGridModel>
            arrayListInstance = null;                                                       // lazy load

    public CurrentGridUniqueJoinedGridModels(@NonNull final StringGetter stringGetter) {
        super(stringGetter);
    }

    @NonNull
    private ArrayList<
            CurrentGridUniqueJoinedGridModel> arrayList() {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance = new ArrayList<
                    CurrentGridUniqueJoinedGridModel>();
        return this.arrayListInstance;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean isInRange(final int i) {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
                false : i < this.arrayListInstance.size();
    }

    // region Overridden Methods
    // region java.lang.Iterable<> Overridden Method
    @Override
    @NonNull
    public Iterator<
            CurrentGridUniqueJoinedGridModel> iterator() {
        @SuppressWarnings({"ClassExplicitlyExtendsObject"})
        class Iterator extends Object implements
                java.util.Iterator<CurrentGridUniqueJoinedGridModel> {
            @NonNull
            private final ListIterator<
                    CurrentGridUniqueJoinedGridModel> listIterator;

            private Iterator(@NonNull final ArrayList<
                    CurrentGridUniqueJoinedGridModel> arrayList) {
                super();
                this.listIterator = arrayList.listIterator();
            }

            // region Overridden Methods
            @Override
            public boolean hasNext() {
                return this.listIterator.hasNext();
            }

            @Override
            public CurrentGridUniqueJoinedGridModel next() {
                return this.listIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    @Override
    public boolean add(
            final JoinedGridModel joinedGridModel) {
        if (joinedGridModel instanceof
                CurrentGridUniqueJoinedGridModel)
            return this.arrayList().add(
                    (CurrentGridUniqueJoinedGridModel)
                            joinedGridModel);
        else
            return false;
    }

    @Override
    @IntRange(from = 0)
    public int size() {
        return null == this.arrayListInstance ? 0 : this.arrayListInstance.size();
    }

    @Override
    @Nullable
    public JoinedGridModel get(
            @IntRange(from = 0) final int i) {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @Override
    public void processAll(
            final CurrentGridUniqueJoinedGridModels.Processor processor) {
        if (null != processor)
            for (final CurrentGridUniqueJoinedGridModel
                    currentGridUniqueJoinedGridModel : this)
                processor.process(currentGridUniqueJoinedGridModel);
    }
    // endregion
}