package org.wheatgenetics.coordinate.gc;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter;

public class StatelessGridCreator extends GridCreator {
    private StatelessProjectSetter
            statelessProjectSetterInstance = null;                                          // lazy load

    // region Constructors
    public StatelessGridCreator(final Activity activity,
                                @Types.RequestCode final int requestCode,
                                @Nullable final
                                StatelessGridCreator.Handler handler) {
        super(activity, requestCode, handler);
    }

    @SuppressWarnings({"unused"})
    public StatelessGridCreator(final Activity activity,
                                @Types.RequestCode final int requestCode) {
        this(activity, requestCode, null);
    }

    @NonNull
    private StatelessProjectSetter statelessProjectSetter() {
        if (null == this.statelessProjectSetterInstance) this.statelessProjectSetterInstance =
                new StatelessProjectSetter(this.activity(),
                        new StatelessProjectSetter.Handler() {
                            @Override
                            public void handleNoProjectSet() {
                                StatelessGridCreator
                                        .this.handleNoProjectSet();
                            }

                            @Override
                            public void handleProjectSet(
                                    @IntRange(from = 1) final long projectId,
                                    final boolean existing) {
                                StatelessGridCreator.this.handleProjectSet(
                                        projectId, existing);
                            }
                        });
        return this.statelessProjectSetterInstance;
    }
    // endregion

    // region Public Methods
    public void create() {
        this.setTemplateIdStatusToCleared();
        this.statelessProjectSetter().set();
    }

    public void createFromTemplate(@IntRange(from = 1) final long templateId) {
        this.setTemplateId(templateId);
        this.setTemplateIdStatusToSet();
        this.statelessProjectSetter().set();
    }

    public void createInProject(@IntRange(from = 1) final long projectId) {
        this.setTemplateIdStatusToCleared();
        this.handleProjectSet(projectId, /* existing => */true);
    }
    // endregion
}