package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.content.Context
 * android.os.Bundle
 * android.preference.Fragment
 * android.preference.ListPreference
 * android.preference.Preference.OnPreferenceClickListener
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 */
public class PreferenceFragment extends android.preference.PreferenceFragment
{
    private android.preference.Preference.OnPreferenceClickListener
        onPreferenceClickListener = null;

    // region Overridden Methods
    @java.lang.Override public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof android.preference.Preference.OnPreferenceClickListener)
            this.onPreferenceClickListener =
                (android.preference.Preference.OnPreferenceClickListener) context;
        else
            throw new java.lang.RuntimeException(null == context ?
                "context" : context.toString() + " must implement OnPreferenceClickListener");
    }

    @java.lang.Override public void onCreate(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource.
        this.addPreferencesFromResource(org.wheatgenetics.coordinate.R.xml.preferences);

        final android.preference.ListPreference listPreference =
            (android.preference.ListPreference) this.findPreference("Uniqueness");
        if (null != listPreference)
            listPreference.setOnPreferenceClickListener(this.onPreferenceClickListener);
    }

    @java.lang.Override public void onDetach()
    { this.onPreferenceClickListener = null; super.onDetach(); }
    // endregion
}