package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class JoinedGridModels extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.JoinedGridModel>
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Processor
    {
        public abstract void process(
            org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel);
    }

    private java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel>
        arrayListInstance = null;

    // region Private Methods
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel> arrayList()
    {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
                new java.util.ArrayList<org.wheatgenetics.coordinate.model.JoinedGridModel>();
        return this.arrayListInstance;
    }

    private boolean isInRange(final int i)
    {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @android.support.annotation.NonNull @java.lang.Override
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
            @java.lang.Override public boolean hasNext() { return this.listIterator.hasNext(); }

            @java.lang.Override public org.wheatgenetics.coordinate.model.JoinedGridModel next()
            { return this.listIterator.next(); }

            @java.lang.Override public void remove()
            { throw new java.lang.UnsupportedOperationException(); }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    boolean export(final java.io.File exportFile, final java.lang.String exportFileName,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        final boolean success;
        if (null == exportFile || null == helper)
            success = false;
        else
        {
            {
                final java.lang.String string;
                {
                    final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
                          boolean                 first         = true                         ;
                    for (final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel:
                    this)
                    {
                        final java.io.StringWriter stringWriter = new java.io.StringWriter();
                        joinedGridModel.export(stringWriter, exportFileName,      // throws java.io-
                            helper, /* includeHeader => */ first);                //  .IOException
                        stringBuilder.append(stringWriter.toString());
                        if (first) first = false;
                    }
                    string = stringBuilder.toString();
                }

                final java.io.FileOutputStream fileOutputStream =
                    new java.io.FileOutputStream(exportFile);
                // noinspection TryFinallyCanBeTryWithResources
                try     { fileOutputStream.write(string.getBytes()); }
                finally { fileOutputStream.close()                 ; }
            }
            success = true;
        }
        return success;
    }

    // region Public Methods
    public boolean add(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        // noinspection SimplifiableConditionalExpression
        return null == joinedGridModel ? false : this.arrayList().add(joinedGridModel);
    }

    public @android.support.annotation.IntRange(from = 0) int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    public org.wheatgenetics.coordinate.model.JoinedGridModel get(
    @android.support.annotation.IntRange(from = 0) final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    public java.lang.String[] names()
    {
        final @android.support.annotation.IntRange(from = 0) int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final java.lang.String result[] = new java.lang.String[size];
            {
                final int first = 0, last = size - 1;
                for (@android.support.annotation.IntRange(from = 0) int i = first; i <= last; i++)
                    result[i] = this.get(i).name();
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
        final @android.support.annotation.IntRange(from = 0) int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final org.wheatgenetics.coordinate.model.Cells result =
                new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);
            {
                final int first = 0, last = size - 1;
                for (@android.support.annotation.IntRange(from = 0) int i = first; i <= last; i++)
                    result.accumulate(this.get(i).excludedCellsFromEntries());
            }
            return result;
        }
    }
    // endregion
}