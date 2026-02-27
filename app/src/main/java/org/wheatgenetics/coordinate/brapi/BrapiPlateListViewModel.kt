package org.wheatgenetics.coordinate.brapi

import androidx.lifecycle.ViewModel
import org.brapi.v2.model.geno.BrAPIPlate

class BrapiPlateListViewModel : ViewModel() {

    var plates: List<BrAPIPlate>? = null

    /**
     * Holds filter selections for one dimension.
     * [ids] are used for matching against plate fields (programDbId, trialDbId, studyDbId, or
     * derived studyDbIds for season/crop).
     * [names] are human-readable labels shown in filter chips.
     * [nameToIds] maps each display name to the list of filter IDs it represents, so
     * individual chips can be removed without clearing the whole filter.
     */
    data class FilterState(
        val ids: MutableSet<String> = mutableSetOf(),
        val names: MutableSet<String> = mutableSetOf(),
        val nameToIds: MutableMap<String, List<String>> = mutableMapOf(),
    ) {
        fun isActive() = ids.isNotEmpty()
        fun clear() { ids.clear(); names.clear(); nameToIds.clear() }
        fun set(newIds: List<String>, newNames: List<String>) {
            ids.clear(); ids.addAll(newIds)
            names.clear(); names.addAll(newNames)
            nameToIds.clear()
            newNames.zip(newIds).forEach { (name, id) ->
                nameToIds[name] = (nameToIds[name] ?: emptyList()) + id
            }
        }
        /** Remove a single named entry and its associated IDs. */
        fun removeName(name: String) {
            val idsToRemove = nameToIds.remove(name) ?: return
            names.remove(name)
            ids.removeAll(idsToRemove.toSet())
        }
    }

    val programFilter = FilterState()
    val trialFilter = FilterState()
    val studyFilter = FilterState()

    fun hasActiveFilters(): Boolean =
        programFilter.isActive() || trialFilter.isActive() || studyFilter.isActive()

    fun clearAllFilters() {
        listOf(programFilter, trialFilter, studyFilter).forEach { it.clear() }
    }

    fun filteredAndSearched(query: String): List<BrAPIPlate> {
        val all = plates ?: return emptyList()
        return all.filter { plate -> matchesFilters(plate) && matchesSearch(plate, query) }
    }

    private fun matchesFilters(plate: BrAPIPlate): Boolean {
        if (programFilter.isActive()) {
            if (plate.programDbId == null || plate.programDbId !in programFilter.ids) return false
        }
        if (trialFilter.isActive()) {
            if (plate.trialDbId == null || plate.trialDbId !in trialFilter.ids) return false
        }
        if (studyFilter.isActive()) {
            if (plate.studyDbId == null || plate.studyDbId !in studyFilter.ids) return false
        }
        return true
    }

    private fun matchesSearch(plate: BrAPIPlate, query: String): Boolean {
        if (query.isBlank()) return true
        val q = query.trim()
        return listOfNotNull(
            plate.plateName,
            plate.plateDbId,
            plate.studyDbId,
            plate.programDbId,
            plate.trialDbId,
        ).any { it.contains(q, ignoreCase = true) }
    }
}
