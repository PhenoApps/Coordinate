package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class JoinedGridModels extends BaseJoinedGridModels
        implements Iterable<JoinedGridModel> {
    private ArrayList<JoinedGridModel>
            arrayListInstance = null;                                                       // lazy load

    public JoinedGridModels(@NonNull final StringGetter stringGetter) {
        super(stringGetter);
    }

    @NonNull
    private ArrayList<JoinedGridModel> arrayList() {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
                    new ArrayList<JoinedGridModel>();
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
    public Iterator<JoinedGridModel> iterator() {
        @SuppressWarnings({"ClassExplicitlyExtendsObject"})
        class Iterator extends Object
                implements java.util.Iterator<JoinedGridModel> {
            @NonNull
            private final
            ListIterator<JoinedGridModel>
                    listIterator;

            private Iterator(@NonNull final ArrayList<JoinedGridModel> arrayList) {
                super();
                this.listIterator = arrayList.listIterator();
            }

            // region Overridden Methods
            @Override
            public boolean hasNext() {
                return this.listIterator.hasNext();
            }

            @Override
            public JoinedGridModel next() {
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
        // noinspection SimplifiableConditionalExpression
        return null == joinedGridModel ? false : this.arrayList().add(joinedGridModel);
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
            final JoinedGridModels.Processor processor) {
        if (null != processor)
            for (final JoinedGridModel joinedGridModel : this)
                processor.process(joinedGridModel);
    }

    public void sort(java.util.Comparator<JoinedGridModel> comparator) {
        if (null != this.arrayListInstance && null != comparator)
            java.util.Collections.sort(this.arrayListInstance, comparator);
    }
    // endregion
}