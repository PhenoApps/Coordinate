package org.wheatgenetics.androidlibrary;

/**
 * When Ruban barcode scanners are in automatic mode and the user scans barcodes quickly the scanner
 * sometimes produces truncated/incorrect text.  This class decreases the occurrence of this problem
 * by building in a delay.
 *
 * Uses:
 * android.os.Handler
 * android.widget.EditText
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.EditorActionListener
 */
public class DebouncingEditorActionListener
extends EditorActionListener
{
    @SuppressWarnings({"ClassExplicitlyExtendsObject"})
    private static class TextAccumulator extends Object
    {
        @SuppressWarnings({"UnnecessaryInterfaceModifier"}) private interface Logger
        { public void log(String msg); }

        // region Fields
        @androidx.annotation.Nullable private final
            Receiver receiver   ;
                                                private final boolean                debug      ;
        @androidx.annotation.IntRange(from = 0) private final long                   delayMillis;
        @androidx.annotation.NonNull            private final
            Logger
            logger;

        private boolean accumulating = false;

        @SuppressWarnings({"Convert2Diamond"}) private final java.util.ArrayList<
            String> arrayList = new java.util.ArrayList<String>();

        private final Runnable runnable = new Runnable()
            {
                @Override public void run()
                {
                    TextAccumulator.this.stop();
                }
            };
        private final android.os.Handler handler = new android.os.Handler();
        // endregion

        private void stop()
        {
            this.accumulating = false;

            if (this.debug)
            {
                final StringBuilder stringBuilder = new StringBuilder();
                {
                    boolean firstText = true;
                    for (final String text: this.arrayList)
                    {
                        if (firstText) firstText = false; else stringBuilder.append(", ");
                        stringBuilder.append(text);
                    }
                }
                this.logger.log(stringBuilder.toString());
            }

            if (null != this.receiver)
            {
                int longest;
                {
                    final int first = 0, last = this.arrayList.size() - 1;
                    @androidx.annotation.IntRange(from = 0) int maxLength = 0;

                    longest = first;
                    for (int i = first; i <= last; i++)
                    {
                        final int length = this.arrayList.get(i).length();
                        if (length > maxLength) { maxLength = length; longest = i; }
                    }
                }
                this.receiver.receiveText(this.arrayList.get(longest));
            }
        }

        private TextAccumulator(@androidx.annotation.Nullable
            final Receiver receiver,
        final boolean debug, @androidx.annotation.IntRange(from = 0) final long delayMillis,
        @androidx.annotation.NonNull final
            Logger
            logger)
        {
            super();

            this.receiver    = receiver   ; this.debug  = debug ;
            this.delayMillis = delayMillis; this.logger = logger;
        }

        private void accumulate(final String text)
        {
            if (!this.accumulating)
            {
                this.arrayList.clear();
                this.accumulating = true;
                this.handler.postDelayed(this.runnable, /* delayMillis => */ this.delayMillis);
            }
            this.arrayList.add(text);
        }
    }

    private final TextAccumulator
        textAccumulator;

    public DebouncingEditorActionListener(
    @androidx.annotation.NonNull  final android.widget.EditText editText,
    @androidx.annotation.Nullable final
        Receiver receiver,
    final boolean debug, @androidx.annotation.IntRange(from = 0) final long delayMillis)
    {
        super(editText, receiver, debug);
        this.textAccumulator =
            new TextAccumulator(
                receiver, debug, delayMillis, new TextAccumulator.Logger()
                {
                    @Override public void log(final String msg)
                    { DebouncingEditorActionListener.log(msg); }
                });
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void sendText(final String text) { this.textAccumulator.accumulate(text); }
}