package org.wheatgenetics.coordinate.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A RecyclerView that can be forced to measure itself at an exact width,
 * bypassing the HorizontalScrollView parent which always passes UNSPECIFIED.
 */
public class FitToWidthRecyclerView extends RecyclerView {

    private boolean mFitToWidth = false;
    private int mFitWidth = 0;

    public FitToWidthRecyclerView(Context context) {
        super(context);
    }

    public FitToWidthRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitToWidthRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setFitToWidth(boolean fit, int width) {
        mFitToWidth = fit;
        mFitWidth = width;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (mFitToWidth && mFitWidth > 0) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(mFitWidth, View.MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
