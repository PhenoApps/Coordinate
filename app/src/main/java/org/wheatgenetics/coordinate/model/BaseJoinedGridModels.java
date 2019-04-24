package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel.Helper
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class BaseJoinedGridModels extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Processor
    {
        public abstract void process(
        org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract boolean isInRange(final int i);

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
                    class Processor extends java.lang.Object
                    implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
                    {
                        // region Fields
                        private final java.lang.String exportFileName;
                        @android.support.annotation.NonNull private final java.lang.StringBuilder
                            stringBuilder = new java.lang.StringBuilder();
                        @android.support.annotation.NonNull private final
                            org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper;

                        private boolean first = true;
                        // endregion

                        private Processor(final java.lang.String exportFileName,
                        @android.support.annotation.NonNull final
                            org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
                        { super(); this.exportFileName = exportFileName; this.helper = helper; }

                        // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
                        @java.lang.Override public void process(
                        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
                        {
                            if (null != joinedGridModel)
                            {
                                final java.io.StringWriter stringWriter =
                                    new java.io.StringWriter();
                                try
                                {
                                    joinedGridModel.export(stringWriter,          // throws java.io-
                                        this.exportFileName, this.helper,         //  .IOException
                                        /* includeHeader => */ this.first);
                                }
                                catch (final java.io.IOException e) { /* Do not process. */ }

                                this.stringBuilder.append(stringWriter.toString());
                                if (this.first) this.first = false;
                            }
                        }
                        // endregion

                        private java.lang.String getString()
                        { return this.stringBuilder.toString(); }
                    }
                    final Processor processor = new Processor(exportFileName, helper);
                    this.processAll(processor);
                    string = processor.getString();
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
    public abstract boolean add(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel);

    @android.support.annotation.IntRange(from = 0) public abstract int size();

    @android.support.annotation.Nullable
    public abstract org.wheatgenetics.coordinate.model.JoinedGridModel get(
    @android.support.annotation.IntRange(from = 0) final int i);

    @android.support.annotation.Nullable public java.lang.String[] names()
    {
        @android.support.annotation.IntRange(from = 0) final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String result[] = new java.lang.String[size];
            {
                final int first = 0, last = size - 1;
                for (@android.support.annotation.IntRange(from = 0) int i = first; i <= last; i++)
                {
                    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                        this.get(i);
                    if (null != joinedGridModel) result[i] = joinedGridModel.name();
                }
            }
            return result;
        }
    }

    public abstract void processAll(
    final org.wheatgenetics.coordinate.model.JoinedGridModels.Processor processor);

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.Cells excludedCells(
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        @android.support.annotation.IntRange(from = 0) final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final org.wheatgenetics.coordinate.model.Cells result =
                new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);
            {
                final int first = 0, last = size - 1;
                for (@android.support.annotation.IntRange(from = 0) int i = first; i <= last; i++)
                {
                    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                        this.get(i);
                    if (null != joinedGridModel)
                        result.accumulate(joinedGridModel.excludedCellsFromEntries());
                }
            }
            return result;
        }
    }
    // endregion
}