package org.wheatgenetics.coordinate.brapi.service

import android.content.Context
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.brapi.client.v2.model.queryParams.genotype.PlatesQueryParams
import org.brapi.client.v2.model.queryParams.genotype.SampleQueryParams
import org.brapi.client.v2.modules.genotype.PlatesApi
import org.brapi.client.v2.modules.genotype.SamplesApi
import org.brapi.v2.model.geno.BrAPIPlate
import org.brapi.v2.model.geno.BrAPISample
import org.wheatgenetics.coordinate.preference.GeneralKeys

class BrapiGenotypingService(context: Context) {

    private val client = BrapiClientFactory.buildClient(context)
    private val platesApi = PlatesApi(client)
    private val samplesApi = SamplesApi(client)
    private val pageSize: Int =
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString(GeneralKeys.BRAPI_PAGE_SIZE, "50")?.toIntOrNull() ?: 50

    /**
     * Fetches all plates from the server, handling pagination automatically.
     * Optional filters: studyDbId, programDbId, plateName.
     */
    suspend fun getPlates(
        studyDbId: String? = null,
        programDbId: String? = null,
        plateName: String? = null,
    ): List<BrAPIPlate> = withContext(Dispatchers.IO) {
        val result = mutableListOf<BrAPIPlate>()
        var page = 0
        var totalPages = 1

        while (page < totalPages) {
            val params = PlatesQueryParams().apply {
                studyDbId?.let { studyDbId(it) }
                programDbId?.let { programDbId(it) }
                plateName?.let { plateName(it) }
                page(page)
                pageSize(pageSize)
            }
            val response = platesApi.platesGet(params)
            val body = response.body ?: break
            result.addAll(body.result?.data ?: emptyList())
            totalPages = body.metadata?.pagination?.totalPages ?: 1
            page++
        }
        result
    }

    /**
     * Fetches a single plate by its ID.
     */
    suspend fun getPlate(plateDbId: String): BrAPIPlate? = withContext(Dispatchers.IO) {
        val response = platesApi.platesPlateDbIdGet(plateDbId)
        response.body?.result
    }

    /**
     * Fetches all samples for a given plate, handling pagination.
     */
    suspend fun getSamplesForPlate(plateDbId: String): List<BrAPISample> =
        withContext(Dispatchers.IO) {
            val result = mutableListOf<BrAPISample>()
            var page = 0
            var totalPages = 1

            while (page < totalPages) {
                val params = SampleQueryParams().apply {
                    plateDbId(plateDbId)
                    page(page)
                    pageSize(pageSize)
                }
                val response = samplesApi.samplesGet(params)
                val body = response.body ?: break
                result.addAll(body.result?.data ?: emptyList())
                totalPages = body.metadata?.pagination?.totalPages ?: 1
                page++
            }
            result
        }

    /**
     * Creates new samples on the server and returns the created samples (with assigned sampleDbIds).
     */
    suspend fun createSamples(samples: List<BrAPISample>): List<BrAPISample> =
        withContext(Dispatchers.IO) {
            val response = samplesApi.samplesPost(samples)
            response.body?.result?.data ?: emptyList()
        }

    /**
     * Updates an existing sample on the server.
     * Uses the batch PUT endpoint.
     */
    suspend fun updateSample(sampleDbId: String, sample: BrAPISample): BrAPISample? =
        withContext(Dispatchers.IO) {
            val body = mapOf(sampleDbId to sample)
            val response = samplesApi.samplesPut(body)
            response.body?.result?.data?.firstOrNull()
        }
}
