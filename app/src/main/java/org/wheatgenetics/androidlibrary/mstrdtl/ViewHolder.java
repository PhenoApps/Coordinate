package org.wheatgenetics.androidlibrary.mstrdtl;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.recyclerview.widget.RecyclerView.ViewHolder
 *
 * org.wheatgenetics.javalib.mstrdtl.Item
 *
 * org.wheatgenetics.androidlibrary.R
 */
class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder
{
    private final android.widget.TextView positionTextView, titleTextView;

    ViewHolder(@androidx.annotation.NonNull final android.view.View itemView)
    {
        super(itemView);

        this.positionTextView = this.itemView.findViewById(
            R.id.masterDetailListItemPositionTextView);
        this.titleTextView = this.itemView.findViewById(
            R.id.masterDetailListItemTitleTextView);
    }

    void bind(
    @androidx.annotation.Nullable final org.wheatgenetics.javalib.mstrdtl.Item item           ,
    @androidx.annotation.Nullable final android.view.View.OnClickListener      onClickListener)
    {
        {
            @androidx.annotation.Nullable final String position, title;
            if (null == item)
                position = title = null;
            else
                { position = item.getPositionAsString(); title = item.getTitle(); }

            if (null != this.positionTextView) this.positionTextView.setText(position);
            if (null != this.titleTextView   ) this.titleTextView.setText   (title   );
        }

        this.itemView.setOnClickListener(onClickListener);
    }
}