package org.wheatgenetics.coordinate.projects;

import androidx.annotation.IntRange;

import org.wheatgenetics.coordinate.viewmodel.ViewModel;

public class ProjectsViewModel extends ViewModel {
    private String directoryName;

    // region Package Methods
    void setProjectIdAndDirectoryName(@IntRange(from = 1) final long projectId,
                                      final String directoryName) {
        this.setId(projectId);
        this.directoryName = directoryName;
    }

    String getDirectoryName() {
        return this.directoryName;
    }
    // endregion
}