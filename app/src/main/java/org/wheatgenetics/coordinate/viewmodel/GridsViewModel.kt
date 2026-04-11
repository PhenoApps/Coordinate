package org.wheatgenetics.coordinate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.wheatgenetics.coordinate.data.model.JoinedGridData
import org.wheatgenetics.coordinate.data.repository.GridsRepository
import javax.inject.Inject

@HiltViewModel
class GridsViewModel @Inject constructor(
    private val repo: GridsRepository
) : ViewModel() {
    val grids: LiveData<List<JoinedGridData>> = repo.getAllJoinedGrids()

    fun delete(id: Long) = viewModelScope.launch {
        val grid = repo.getJoinedGridById(id)
        grid?.grid?.let { repo.deleteGrid(it) }
    }
}