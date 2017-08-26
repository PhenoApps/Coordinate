package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.widget.EditText
 *
 * org.wheatgenetics.javalib.Utils
 */
class Utils extends java.lang.Object
{
    static java.lang.String getText(final android.widget.EditText editText)
    {
        assert null != editText;
        return org.wheatgenetics.javalib.Utils.adjust(editText.getText().toString());
    }
}