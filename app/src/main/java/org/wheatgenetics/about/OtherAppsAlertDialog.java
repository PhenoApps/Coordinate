package org.wheatgenetics.about;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.net.Uri
 * android.view.InflateException
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.AdapterView
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ArrayAdapter
 * android.widget.ImageView
 * android.widget.ListView
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.about.OtherApps
 * org.wheatgenetics.about.OtherApps.Index
 */
public class OtherAppsAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    public OtherAppsAlertDialog(
    @androidx.annotation.NonNull final android.app.Activity                    activity     ,
    @androidx.annotation.NonNull final OtherApps.Index suppressIndex)
    {
        super(activity);

        @SuppressWarnings({"ViewConstructor"})
        class ListView extends android.widget.ListView
        {
            private ListView(final android.content.Context context,
            @androidx.annotation.NonNull
                final OtherApps.Index suppressIndex)
            {
                super(context); this.setDivider(null); this.setDividerHeight(0);

                final OtherApps otherApps =
                    new OtherApps(suppressIndex);

                {
                    class ArrayAdapter extends android.widget.ArrayAdapter<String>
                    {
                        @androidx.annotation.NonNull
                        private final OtherApps otherApps;

                        private ArrayAdapter(final android.content.Context context,
                        @androidx.annotation.NonNull
                            final OtherApps otherApps)
                        {
                            super(
                                /* context  => */ context,
                                /* resource => */ R.layout.alert_dialog_other_apps);

                            this.otherApps = otherApps;
                            this.addAll(this.otherApps.getTexts());
                        }

                        @android.annotation.SuppressLint({"InflateParams", "ViewHolder"})
                        @Override public @androidx.annotation.NonNull android.view.View
                        getView(final int position, final android.view.View convertView,
                        @androidx.annotation.NonNull final android.view.ViewGroup parent)
                        {
                            final android.view.View result;
                            {
                                final android.view.LayoutInflater layoutInflater =
                                    (android.view.LayoutInflater)
                                        this.getContext().getSystemService(
                                            android.content.Context.LAYOUT_INFLATER_SERVICE);
                                if (null == layoutInflater)
                                    throw new android.view.InflateException();
                                else
                                    result = layoutInflater.inflate(R.layout.alert_dialog_other_apps,
                                        null,true);
                            }
                            {
                                final android.widget.TextView textView = result.findViewById(
                                    R.id.otherAppsTextView);
                                if (null != textView)
                                    textView.setText(this.otherApps.getTexts()[position]);
                            }
                            {
                                final android.widget.ImageView imageView = result.findViewById(
                                    R.id.otherAppsImageView);
                                if (null != imageView) imageView.setImageResource(
                                    this.otherApps.getResIds()[position]);
                            }
                            return result;
                        }
                    }
                    this.setAdapter(new ArrayAdapter(context, otherApps));
                }

                @SuppressWarnings({"ClassExplicitlyExtendsObject"})
                class OnItemClickListener extends Object
                implements android.widget.AdapterView.OnItemClickListener
                {
                    // region Fields
                    @androidx.annotation.NonNull private final android.content.Context context;

                    @androidx.annotation.NonNull
                    private final OtherApps otherApps;
                    // endregion

                    private OnItemClickListener(
                    @androidx.annotation.NonNull final android.content.Context           context  ,
                    @androidx.annotation.NonNull final OtherApps otherApps)
                    { super(); this.context = context; this.otherApps = otherApps; }

                    @Override public void onItemClick(
                    final android.widget.AdapterView<?> parent, final android.view.View view,
                    final int position, final long id)
                    {
                        this.context.startActivity(new android.content.Intent(
                            android.content.Intent.ACTION_VIEW                       ,
                            android.net.Uri.parse(this.otherApps.getUris()[position])));
                    }
                }
                this.setOnItemClickListener(new OnItemClickListener(context, otherApps));
            }
        }
        this.setView(new ListView(this.activity(), suppressIndex));
    }

    @Override public void configure()
    {
        this.setTitle(R.string.otherAppsAlertDialogTitle)
            .setCancelableToTrue().setOKNegativeButton();
    }
}