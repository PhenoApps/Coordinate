package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
public class CurrentGridUniqueJoinedGridModels
extends org.wheatgenetics.coordinate.model.BaseJoinedGridModels
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel>
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel>
        arrayListInstance = null;                                                       // lazy load

    @androidx.annotation.NonNull private java.util.ArrayList<
    org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel> arrayList()
    {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance = new java.util.ArrayList<
                org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel>();
        return this.arrayListInstance;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean isInRange(final int i)
    {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }

    // region Overridden Methods
    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override @androidx.annotation.NonNull public java.util.Iterator<
    org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel> iterator()
    {
        @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
        class Iterator extends java.lang.Object implements
        java.util.Iterator<org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel>
        {
            @androidx.annotation.NonNull private final java.util.ListIterator<
                org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel> listIterator;

            private Iterator(@androidx.annotation.NonNull final java.util.ArrayList<
            org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel> arrayList)
            { super(); this.listIterator = arrayList.listIterator(); }

            // region Overridden Methods
            @java.lang.Override public boolean hasNext() { return this.listIterator.hasNext(); }

            @java.lang.Override
            public org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel next()
            { return this.listIterator.next(); }

            @java.lang.Override public void remove()
            { throw new java.lang.UnsupportedOperationException(); }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    @java.lang.Override public boolean add(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        if (joinedGridModel instanceof
        org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
            return this.arrayList().add(
                (org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                joinedGridModel);
        else
            return false;
    }

    @java.lang.Override @androidx.annotation.IntRange(from = 0) public int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    @java.lang.Override @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.model.JoinedGridModel get(
    @androidx.annotation.IntRange(from = 0) final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @java.lang.Override public void processAll(
    final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModels.Processor processor)
    {
        if (null != processor)
            for (final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
            currentGridUniqueJoinedGridModel: this)
                processor.process(currentGridUniqueJoinedGridModel);
    }
    // endregion
}