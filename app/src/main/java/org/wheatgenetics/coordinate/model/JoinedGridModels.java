package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class JoinedGridModels extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.JoinedGridModel>
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Processor
    {
        public abstract void process(
            org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel);
    }

    private java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel>
        arrayListInstance = null;

    // region Private Methods
    @java.lang.SuppressWarnings("Convert2Diamond")
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel> arrayList()
    {
        if (null == this.arrayListInstance) this.arrayListInstance =
            new java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel>();
        return this.arrayListInstance;
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    private boolean isInRange(final int i)
    {
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override
    public java.util.Iterator<org.wheatgenetics.coordinate.model.JoinedGridModel> iterator()
    {
        class Iterator extends java.lang.Object
        implements java.util.Iterator<org.wheatgenetics.coordinate.model.JoinedGridModel>
        {
            private final java.util.ListIterator<org.wheatgenetics.coordinate.model.JoinedGridModel>
                listIterator;

            private Iterator(
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel> arrayList)
            { super(); assert null != arrayList; this.listIterator = arrayList.listIterator(); }

            // region Overridden Methods
            @java.lang.Override
            public boolean hasNext() { return this.listIterator.hasNext(); }

            @java.lang.Override
            public org.wheatgenetics.coordinate.model.JoinedGridModel next()
            { return this.listIterator.next(); }

            @java.lang.Override
            public void remove() { throw new java.lang.UnsupportedOperationException(); }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    // region Public Methods
    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean add(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { return null == joinedGridModel ? false : this.arrayList().add(joinedGridModel); }

    public int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    public org.wheatgenetics.coordinate.model.JoinedGridModel get(final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    public java.lang.String[] names()
    {
        final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final java.lang.String result[] = new java.lang.String[size];
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++) result[i] = this.get(i).name();
            }
            return result;
        }
    }

    public void processAll(
    final org.wheatgenetics.coordinate.model.JoinedGridModels.Processor processor)
    {
        if (null != processor)
            for (final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel: this)
                processor.process(joinedGridModel);
    }

    public org.wheatgenetics.coordinate.model.Cells excludedCells(
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final org.wheatgenetics.coordinate.model.Cells result =
                new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++)
                    result.accumulate(this.get(i).excludedCellsFromEntries());
            }
            return result;
        }
    }
    // endregion
}