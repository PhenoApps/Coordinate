package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.preference.Fragment
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 */
public class PreferenceFragment extends android.preference.PreferenceFragment
{
    @java.lang.Override public void onCreate(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource.
        this.addPreferencesFromResource(org.wheatgenetics.coordinate.R.xml.preferences);
    }
}