package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public abstract class BaseJoinedGridModels {
    @NonNull
    private final StringGetter
            stringGetter;

    public BaseJoinedGridModels(@NonNull final StringGetter stringGetter) {
        super();
        this.stringGetter = stringGetter;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @SuppressWarnings({"unused"})
    abstract boolean isInRange(final int i);

    // region Public Methods
    public abstract boolean add(
            final JoinedGridModel joinedGridModel);

    @IntRange(from = 0)
    public abstract int size();

    @Nullable
    public abstract JoinedGridModel get(
            @IntRange(from = 0) final int i);

    @Nullable
    public String[] names() {
        @IntRange(from = 0) final int size = this.size();

        if (size <= 0)
            return null;
        else {
            // noinspection CStyleArrayDeclaration
            final String result[] = new String[size];
            {
                final int first = 0, last = size - 1;
                for (@IntRange(from = 0) int i = first; i <= last; i++) {
                    final JoinedGridModel joinedGridModel =
                            this.get(i);
                    if (null != joinedGridModel) result[i] = joinedGridModel.name();
                }
            }
            return result;
        }
    }

    public abstract void processAll(
            final BaseJoinedGridModels.Processor processor);

    @Nullable
    public Cells excludedCells(
            @IntRange(from = 1) final int maxRow,
            @IntRange(from = 1) final int maxCol) {
        @IntRange(from = 0) final int size = this.size();

        if (size <= 0)
            return null;
        else {
            final Cells result =
                    new Cells(maxRow, maxCol, this.stringGetter);
            {
                final int first = 0, last = size - 1;
                for (@IntRange(from = 0) int i = first; i <= last; i++) {
                    final JoinedGridModel joinedGridModel =
                            this.get(i);
                    if (null != joinedGridModel)
                        result.accumulate(joinedGridModel.excludedCellsFromEntries());
                }
            }
            return result;
        }
    }

    public boolean export(final File exportFile, final String exportFileName,
                          final JoinedGridModel.Helper helper)
            throws IOException {
        final boolean success;
        if (null == exportFile || null == helper)
            success = false;
        else {
            {
                final String string;
                {
                    class Processor extends Object
                            implements BaseJoinedGridModels.Processor {
                        // region Fields
                        private final String exportFileName;

                        @NonNull
                        private final StringBuilder
                                stringBuilder = new StringBuilder();

                        @NonNull
                        private final
                        JoinedGridModel.Helper helper;

                        private boolean first = true;
                        // endregion

                        private Processor(final String exportFileName,
                                          @NonNull final
                                          JoinedGridModel.Helper helper) {
                            super();
                            this.exportFileName = exportFileName;
                            this.helper = helper;
                        }

                        // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
                        @SuppressWarnings({"unused"})
                        @Override
                        public void process(
                                final JoinedGridModel joinedGridModel) {
                            if (null != joinedGridModel) {
                                final StringWriter stringWriter =
                                        new StringWriter();
                                try {
                                    joinedGridModel.export(stringWriter,          // throws java.io-
                                            this.exportFileName, this.helper,         //  .IOException
                                            /* includeHeader => */ this.first);
                                } catch (final IOException e) { /* Do not process. */ }

                                this.stringBuilder.append(stringWriter.toString());
                                if (this.first) this.first = false;
                            }
                        }
                        // endregion

                        private String getString() {
                            return this.stringBuilder.toString();
                        }
                    }
                    final Processor processor = new Processor(exportFileName, helper);
                    this.processAll(processor);
                    string = processor.getString();
                }

                final FileOutputStream fileOutputStream =
                        new FileOutputStream(exportFile);

                // noinspection TryFinallyCanBeTryWithResources
                try {
                    fileOutputStream.write(string.getBytes());
                } finally {
                    fileOutputStream.close();
                }
            }
            success = true;
        }
        return success;
    }

    public boolean export(final OutputStream output, final String exportFileName,
                          final JoinedGridModel.Helper helper)
            throws IOException {
        final boolean success;
        if (null == output || null == helper)
            success = false;
        else {
            {
                final String string;
                {
                    class Processor extends Object
                            implements BaseJoinedGridModels.Processor {
                        // region Fields
                        private final String exportFileName;

                        @NonNull
                        private final StringBuilder
                                stringBuilder = new StringBuilder();

                        @NonNull
                        private final
                        JoinedGridModel.Helper helper;

                        private boolean first = true;
                        // endregion

                        private Processor(final String exportFileName,
                                          @NonNull final
                                          JoinedGridModel.Helper helper) {
                            super();
                            this.exportFileName = exportFileName;
                            this.helper = helper;
                        }

                        // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
                        @SuppressWarnings({"unused"})
                        @Override
                        public void process(
                                final JoinedGridModel joinedGridModel) {
                            if (null != joinedGridModel) {
                                final StringWriter stringWriter =
                                        new StringWriter();
                                try {
                                    joinedGridModel.export(stringWriter,          // throws java.io-
                                            this.exportFileName, this.helper,         //  .IOException
                                            /* includeHeader => */ this.first);
                                } catch (final IOException e) { /* Do not process. */ }

                                this.stringBuilder.append(stringWriter.toString());
                                if (this.first) this.first = false;
                            }
                        }
                        // endregion

                        private String getString() {
                            return this.stringBuilder.toString();
                        }
                    }
                    final Processor processor = new Processor(exportFileName, helper);
                    this.processAll(processor);
                    string = processor.getString();
                }

                try {
                    output.write(string.getBytes());
                } finally {
                    output.close();
                }
            }
            success = true;
        }
        return success;
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Processor {
        public abstract void process(
                JoinedGridModel joinedGridModel);
    }
    // endregion
}