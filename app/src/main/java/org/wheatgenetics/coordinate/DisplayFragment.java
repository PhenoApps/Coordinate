package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 *
 * org.wheatgenetics.coordinate.R
 */
public class DisplayFragment extends android.support.v4.app.Fragment
{
    public DisplayFragment() { /* Required empty public constructor. */ }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.coordinate.R.layout.fragment_display, container, false);
    }
}