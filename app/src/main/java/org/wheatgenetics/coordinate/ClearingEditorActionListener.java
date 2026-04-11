package org.wheatgenetics.coordinate;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Migrated from org.phenoapps.androidlibrary.ClearingEditorActionListener.
 *
 * An OnEditorActionListener that:
 *  - when the text is empty on IME_ACTION_DONE / IME_NULL+ACTION_DOWN, calls
 *    Receiver.clearText()
 *  - otherwise delivers the trimmed text to Receiver.receiveText(String)
 */
@SuppressWarnings({"unused", "ClassExplicitlyExtendsObject"})
public class ClearingEditorActionListener extends Object
        implements TextView.OnEditorActionListener {

    // region Receiver interface
    public interface Receiver {
        void receiveText(String text);
        void clearText();
    }
    // endregion

    // region Fields
    @NonNull  private final EditText editText;
    @Nullable private final Receiver receiver;
    // endregion

    // region Constructor
    public ClearingEditorActionListener(
            @NonNull  final EditText editText,
            @Nullable final Receiver receiver) {
        super();
        this.editText = editText;
        this.receiver = receiver;
        this.editText.setOnEditorActionListener(this);
    }
    // endregion

    // region private helpers
    private static boolean isEmpty(@Nullable final String text) {
        return null == text || text.length() <= 0;
    }

    private void process(@Nullable final String text) {
        if (isEmpty(text)) {
            if (null != this.receiver) this.receiver.clearText();
        } else {
            if (null != this.receiver) this.receiver.receiveText(text);
        }
    }
    // endregion

    // region OnEditorActionListener
    @Override
    public boolean onEditorAction(
            final TextView v,
            final int actionId,
            final KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_NULL:
                if (null == event) throw new AssertionError();
                if (event.getAction() == KeyEvent.ACTION_DOWN) return true;
                // fall through to IME_ACTION_DONE for ACTION_UP
            case EditorInfo.IME_ACTION_DONE: {
                final String text = this.editText.getText() == null
                        ? ""
                        : this.editText.getText().toString().trim();
                this.editText.setText("");
                this.process(text);
                return true;
            }
            default: return false;
        }
    }
    // endregion
}
