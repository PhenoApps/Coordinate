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
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.StatelessTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.StatelessTemplateSetter.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 */
public class StatelessGridCreator extends org.wheatgenetics.coordinate.gc.GridCreator
{
    // region Fields
    private org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
        choosingTemplateSetterInstance = null;                                          // lazy load
    private org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
        statelessProjectSetterInstance = null;                                          // lazy load
    // endregion

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

    public StatelessGridCreator(                    final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode)
    { this(activity, requestCode,null); }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter choosingTemplateSetter()
    {
        if (null == this.choosingTemplateSetterInstance) this.choosingTemplateSetterInstance =
            new org.wheatgenetics.coordinate.gc.ts.StatelessTemplateSetter(
                this.activity(), this.requestCode(),
                new org.wheatgenetics.coordinate.gc.ts.StatelessTemplateSetter.Handler()
                {
                    @java.lang.Override public void handleTemplateSet(
                    @androidx.annotation.IntRange(from = 1) final long templateId)
                    {
                        org.wheatgenetics.coordinate.gc.StatelessGridCreator
                            .this.handleTemplateSet(templateId);
                    }
                });
        return this.choosingTemplateSetterInstance;
    }

    @java.lang.Override boolean choosingTemplateSetterIsNotNull()
    { return null != this.choosingTemplateSetterInstance; }
    // endregion

    public void create() { this.statelessProjectSetter().set(); }
}