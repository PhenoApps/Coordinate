package org.wheatgenetics.coordinate.viewmodel

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.wheatgenetics.coordinate.data.dao.EntryDao
import org.wheatgenetics.coordinate.data.model.Entry
import javax.inject.Inject

@HiltViewModel
class CollectorViewModel @Inject constructor(
    private val entryDao: EntryDao
) : ViewModel() {
    fun getEntriesForGrid(gridId: Long): LiveData<List<Entry>> =
        entryDao.getEntriesByGridId(gridId)
}