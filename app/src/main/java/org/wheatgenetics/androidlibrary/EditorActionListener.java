package org.wheatgenetics.androidlibrary;

/**
 * This class clears its editText's text when done.
 *
 * Uses:
 * android.os.Handler
 * android.widget.EditText
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.DebuggingEditorActionListener
 */
class EditorActionListener extends DebuggingEditorActionListener
{
    // region Fields
    private final Runnable runnable = new Runnable()
    {
        @Override public void run()
        { EditorActionListener.this.clearEditText(); }
    };
    private final android.os.Handler handler = new android.os.Handler();
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @SuppressWarnings({"WeakerAccess"}) protected void clearEditTextText()
    { this.handler.postDelayed(this.runnable, /* delayMillis => */100); }

    @SuppressWarnings({"WeakerAccess"}) public EditorActionListener(
    @androidx.annotation.NonNull  final android.widget.EditText editText,
    @androidx.annotation.Nullable final
        Receiver receiver,
    final boolean debug) { super(editText, receiver, debug); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @Override protected void preprocess() { this.clearEditTextText(); }
}