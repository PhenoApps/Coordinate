package org.wheatgenetics.androidlibrary;

/**
 * Uses:
 * android.widget.EditText
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.DebuggingEditorActionListener
 * org.wheatgenetics.androidlibrary.DebuggingEditorActionListener.Receiver
 */
@SuppressWarnings({"unused"}) public class ClearingEditorActionListener
extends DebuggingEditorActionListener
{
    @SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Receiver
    extends DebuggingEditorActionListener.Receiver
    { public abstract void clearText(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @SuppressWarnings({"WeakerAccess"}) protected void clearText()
    {
        final Receiver receiver =
            (Receiver)
                this.getReceiver();
        if (null != receiver) receiver.clearText();
    }

    public ClearingEditorActionListener(
    @androidx.annotation.NonNull  final android.widget.EditText editText,
    @androidx.annotation.Nullable
        final Receiver receiver,
    final boolean debug) { super(editText, receiver, debug); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @Override protected void process(final String text)
    {
        if (ClearingEditorActionListener.isEmpty(text))
            this.clearText();
        else
            this.sendText(text);
    }
}