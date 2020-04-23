package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
 * org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 */
public class StatelessGridCreator extends org.wheatgenetics.coordinate.gc.GridCreator
{
    private org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
        statelessProjectSetterInstance = null;                                          // lazy load

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter statelessProjectSetter()
    {
        if (null == this.statelessProjectSetterInstance) this.statelessProjectSetterInstance =
            new org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter(this.activity(),
                new org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter.Handler()
                {
                    @java.lang.Override public void handleNoProjectSet()
                    {
                        org.wheatgenetics.coordinate.gc.StatelessGridCreator
                            .this.handleNoProjectSet();
                    }

                    @java.lang.Override public void handleProjectSet(
                    @androidx.annotation.IntRange(from = 1) final long    projectId,
                                                            final boolean existing )
                    {
                        org.wheatgenetics.coordinate.gc.StatelessGridCreator.this.handleProjectSet(
                            projectId, existing);
                    }
                });
        return this.statelessProjectSetterInstance;
    }

    // region Constructors
    public StatelessGridCreator(                    final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.Nullable                   final
        org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler handler)
    { super(activity, requestCode, handler); }

    @java.lang.SuppressWarnings({"unused"})
    public StatelessGridCreator(                    final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode)
    { this(activity, requestCode,null); }
    // endregion

    // region Public Methods
    public void create()
    { this.setTemplateIdStatusToCleared(); this.statelessProjectSetter().set(); }

    public void createFromTemplate(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.setTemplateId(templateId); this.setTemplateIdStatusToSet();
        this.statelessProjectSetter().set();
    }

    public void createInProject(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.setTemplateIdStatusToCleared();
        this.handleProjectSet(projectId, /* existing => */true);
    }
    // endregion
}