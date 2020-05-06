package org.wheatgenetics.coordinate.tc.exclude;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.display.DisplayFragment;
import org.wheatgenetics.coordinate.model.Cell;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.tc.exclude.adapter.Adapter;
import org.wheatgenetics.coordinate.tc.exclude.adapter.DataViewHolder;

public class TemplateDisplayFragment extends DisplayFragment {
    public TemplateDisplayFragment() { /* Required empty public constructor. */ }

    // region Private Methods
    @Nullable
    private TemplateDisplayFragment.Handler templateHandler() {
        return
                (TemplateDisplayFragment.Handler) this.handler;
    }

    private boolean isExcluded(final Cell cell) {
        final TemplateDisplayFragment.Handler
                templateHandler = this.templateHandler();
        if (null == templateHandler)
            throw new NullPointerException();
        else
            return templateHandler.isExcluded(cell);
    }
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected boolean setHandler(
            @NonNull final Context context) {
        final boolean success;

        if (context instanceof
                TemplateDisplayFragment.Handler) {
            this.handler =
                    (TemplateDisplayFragment.Handler) context;
            success = true;
        } else {
            this.handler = null;
            success = false;
        }

        return success;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    protected org.wheatgenetics.coordinate.display.adapter.Adapter makeAdapter(
            @NonNull final DisplayModel displayModel) {
        final Context context = this.getContext();
        if (null == context)
            throw new NullPointerException();
        else
            return new Adapter(
                    displayModel, context,
                    new DataViewHolder.Handler() {
                        @Override
                        public void toggle(@Nullable final ElementModel elementModel) {
                            TemplateDisplayFragment.this.toggle(
                                    elementModel);
                        }
                    },
                    new DataViewHolder.TemplateHandler() {
                        @Override
                        public boolean isExcluded(
                                final Cell cell) {
                            return TemplateDisplayFragment.this.isExcluded(cell);
                        }
                    });
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler extends DisplayFragment.Handler {
        public abstract boolean isExcluded(Cell cell);
    }
    // endregion
}