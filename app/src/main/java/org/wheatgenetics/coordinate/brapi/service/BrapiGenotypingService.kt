package org.wheatgenetics.coordinate.brapi.service

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.brapi.client.v2.model.queryParams.core.ProgramQueryParams
import org.brapi.client.v2.model.queryParams.core.StudyQueryParams
import org.brapi.client.v2.model.queryParams.core.TrialQueryParams
import org.brapi.client.v2.model.queryParams.genotype.PlatesQueryParams
import org.brapi.client.v2.model.queryParams.genotype.SampleQueryParams
import org.brapi.client.v2.modules.core.ProgramsApi
import org.brapi.client.v2.modules.core.StudiesApi
import org.brapi.client.v2.modules.core.TrialsApi
import org.brapi.client.v2.modules.genotype.PlatesApi
import org.brapi.client.v2.modules.genotype.SamplesApi
import org.brapi.v2.model.core.BrAPIProgram
import org.brapi.v2.model.core.BrAPIStudy
import org.brapi.v2.model.core.BrAPITrial
import org.brapi.v2.model.geno.BrAPIPlate
import org.brapi.v2.model.geno.BrAPISample
import org.wheatgenetics.coordinate.preference.GeneralKeys

class BrapiGenotypingService(context: Context) {

    companion object {
        private const val TAG = "BrapiGenotypingService"
    }

    private val client = BrapiClientFactory.buildClient(context)
    private val platesApi = PlatesApi(client)
    private val samplesApi = SamplesApi(client)
    private val studiesApi = StudiesApi(client)
    private val trialsApi = TrialsApi(client)
    private val programsApi = ProgramsApi(client)
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
        Log.d(TAG, "getPlates: studyDbId=$studyDbId, programDbId=$programDbId, plateName=$plateName, pageSize=$pageSize")
        val result = mutableListOf<BrAPIPlate>()
        var page = 0
        var totalPages = 1

        while (page < totalPages) {
            Log.d(TAG, "getPlates: fetching page=$page of totalPages=$totalPages")
            val params = PlatesQueryParams().apply {
                studyDbId?.let { studyDbId(it) }
                programDbId?.let { programDbId(it) }
                plateName?.let { plateName(it) }
                page(page)
                pageSize(pageSize)
            }
            val response = platesApi.platesGet(params)
            Log.d(TAG, "getPlates: response code=${response.statusCode}")
            val body = response.body ?: run {
                Log.w(TAG, "getPlates: null body on page=$page – stopping pagination")
                break
            }
            val pageData = body.result?.data ?: emptyList()
            Log.d(TAG, "getPlates: page=$page returned ${pageData.size} plate(s)")
            val sizeBefore = result.size
            result.addAll(pageData)
            totalPages = body.metadata?.pagination?.totalPages ?: 1
            Log.d(TAG, "getPlates: totalPages=$totalPages after page=$page")
            page++
            // If this page added no new entries, stop to avoid infinite loop on misbehaving servers
            if (result.size == sizeBefore && pageData.isNotEmpty()) {
                Log.w(TAG, "getPlates: page=$page added no new entries – stopping pagination early")
                break
            }
        }
        val deduped = result.distinctBy { it.plateDbId }
        Log.d(TAG, "getPlates: finished – total ${result.size} plate(s), ${deduped.size} unique")
        deduped
    }

    /**
     * Fetches a single plate by its ID.
     *
     * Note: platesPlateDbIdGet is avoided here because PlatesApi.java uses
     * String.replaceAll("\\{plateDbId}", ...) which generates the regex pattern
     * \{plateDbId} — the closing '}' is unescaped, causing a PatternSyntaxException
     * on Android's ICU regex engine. Using platesGet with a plateDbId filter is
     * functionally equivalent and avoids the bug.
     */
    suspend fun getPlate(plateDbId: String): BrAPIPlate? = withContext(Dispatchers.IO) {
        Log.d(TAG, "getPlate: plateDbId=$plateDbId")
        val params = PlatesQueryParams().apply {
            plateDbId(plateDbId)
            pageSize(1)
        }
        val response = platesApi.platesGet(params)
        Log.d(TAG, "getPlate: response code=${response.statusCode}")
        val plate = response.body?.result?.data?.firstOrNull()
        Log.d(TAG, "getPlate: result plateDbId=${plate?.plateDbId}, plateName=${plate?.plateName}, studyDbId=${plate?.studyDbId}")
        plate
    }

    /**
     * Fetches all samples for a given plate, handling pagination.
     */
    suspend fun getSamplesForPlate(plateDbId: String): List<BrAPISample> =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "getSamplesForPlate: plateDbId=$plateDbId, pageSize=$pageSize")
            val result = mutableListOf<BrAPISample>()
            var page = 0
            var totalPages = 1

            while (page < totalPages) {
                Log.d(TAG, "getSamplesForPlate: fetching page=$page of totalPages=$totalPages")
                val params = SampleQueryParams().apply {
                    plateDbId(plateDbId)
                    page(page)
                    pageSize(pageSize)
                }
                val response = samplesApi.samplesGet(params)
                Log.d(TAG, "getSamplesForPlate: response code=${response.statusCode}")
                val body = response.body ?: run {
                    Log.w(TAG, "getSamplesForPlate: null body on page=$page – stopping pagination")
                    break
                }
                val pageData = body.result?.data ?: emptyList()
                Log.d(TAG, "getSamplesForPlate: page=$page returned ${pageData.size} sample(s)")
                result.addAll(pageData)
                totalPages = body.metadata?.pagination?.totalPages ?: 1
                Log.d(TAG, "getSamplesForPlate: totalPages=$totalPages after page=$page")
                page++
            }
            Log.d(TAG, "getSamplesForPlate: finished – total ${result.size} sample(s)")
            result
        }

    /**
     * Fetches all studies from the server, handling pagination.
     * Used to enrich plate metadata with study name, seasons, crop, and trial name.
     */
    suspend fun getStudies(): List<BrAPIStudy> = withContext(Dispatchers.IO) {
        Log.d(TAG, "getStudies: pageSize=$pageSize")
        val result = mutableListOf<BrAPIStudy>()
        var page = 0
        var totalPages = 1

        while (page < totalPages) {
            Log.d(TAG, "getStudies: fetching page=$page of totalPages=$totalPages")
            val params = StudyQueryParams().apply {
                page(page)
                pageSize(pageSize)
            }
            val response = studiesApi.studiesGet(params)
            Log.d(TAG, "getStudies: response code=${response.statusCode}")
            val body = response.body ?: run {
                Log.w(TAG, "getStudies: null body on page=$page – stopping pagination")
                break
            }
            val pageData = body.result?.data ?: emptyList()
            Log.d(TAG, "getStudies: page=$page returned ${pageData.size} study/studies")
            result.addAll(pageData)
            totalPages = body.metadata?.pagination?.totalPages ?: 1
            page++
        }
        Log.d(TAG, "getStudies: finished – total ${result.size} study/studies")
        result
    }

    /**
     * Fetches all trials from the server, handling pagination.
     * Used to enrich plate metadata with trial name and program name.
     */
    suspend fun getTrials(): List<BrAPITrial> = withContext(Dispatchers.IO) {
        Log.d(TAG, "getTrials: pageSize=$pageSize")
        val result = mutableListOf<BrAPITrial>()
        var page = 0
        var totalPages = 1

        while (page < totalPages) {
            Log.d(TAG, "getTrials: fetching page=$page of totalPages=$totalPages")
            val params = TrialQueryParams().apply {
                page(page)
                pageSize(pageSize)
            }
            val response = trialsApi.trialsGet(params)
            Log.d(TAG, "getTrials: response code=${response.statusCode}")
            val body = response.body ?: run {
                Log.w(TAG, "getTrials: null body on page=$page – stopping pagination")
                break
            }
            val pageData = body.result?.data ?: emptyList()
            Log.d(TAG, "getTrials: page=$page returned ${pageData.size} trial(s)")
            result.addAll(pageData)
            totalPages = body.metadata?.pagination?.totalPages ?: 1
            page++
        }
        Log.d(TAG, "getTrials: finished – total ${result.size} trial(s)")
        result
    }

    /**
     * Fetches all programs from the server, handling pagination.
     * Used to build the program filter list.
     */
    suspend fun getPrograms(): List<BrAPIProgram> = withContext(Dispatchers.IO) {
        Log.d(TAG, "getPrograms: pageSize=$pageSize")
        val result = mutableListOf<BrAPIProgram>()
        var page = 0
        var totalPages = 1

        while (page < totalPages) {
            Log.d(TAG, "getPrograms: fetching page=$page of totalPages=$totalPages")
            val params = ProgramQueryParams().apply {
                page(page)
                pageSize(pageSize)
            }
            val response = programsApi.programsGet(params)
            Log.d(TAG, "getPrograms: response code=${response.statusCode}")
            val body = response.body ?: run {
                Log.w(TAG, "getPrograms: null body on page=$page – stopping pagination")
                break
            }
            val pageData = body.result?.data ?: emptyList()
            Log.d(TAG, "getPrograms: page=$page returned ${pageData.size} program(s)")
            result.addAll(pageData)
            totalPages = body.metadata?.pagination?.totalPages ?: 1
            page++
        }
        Log.d(TAG, "getPrograms: finished – total ${result.size} program(s)")
        result
    }

    /**
     * Fetches the name of a study by its ID. Returns null if not found.
     */
    suspend fun getStudyName(studyDbId: String): String? = withContext(Dispatchers.IO) {
        Log.d(TAG, "getStudyName: studyDbId=$studyDbId")
        val params = StudyQueryParams().apply {
            studyDbId(studyDbId)
            pageSize(pageSize)
        }
        val response = studiesApi.studiesGet(params)
        // Explicitly match by ID in case the server ignores the filter param
        val study = response.body?.result?.data?.firstOrNull { it.studyDbId == studyDbId }
            ?: response.body?.result?.data?.firstOrNull()
        Log.d(TAG, "getStudyName: studyName=${study?.studyName}")
        study?.studyName
    }

    /**
     * Fetches the name of a trial by its ID. Returns null if not found.
     */
    suspend fun getTrialName(trialDbId: String): String? = withContext(Dispatchers.IO) {
        Log.d(TAG, "getTrialName: trialDbId=$trialDbId")
        val params = TrialQueryParams().apply {
            trialDbId(trialDbId)
            pageSize(pageSize)
        }
        val response = trialsApi.trialsGet(params)
        // Explicitly match by ID in case the server ignores the filter param
        val trial = response.body?.result?.data?.firstOrNull { it.trialDbId == trialDbId }
            ?: response.body?.result?.data?.firstOrNull()
        Log.d(TAG, "getTrialName: trialName=${trial?.trialName}")
        trial?.trialName
    }

    /**
     * Fetches the name of a program by its ID. Returns null if not found.
     */
    suspend fun getProgramName(programDbId: String): String? = withContext(Dispatchers.IO) {
        Log.d(TAG, "getProgramName: programDbId=$programDbId")
        val params = ProgramQueryParams().apply {
            programDbId(programDbId)
            pageSize(pageSize)
        }
        val response = programsApi.programsGet(params)
        // Explicitly match by ID in case the server ignores the filter param
        val program = response.body?.result?.data?.firstOrNull { it.programDbId == programDbId }
            ?: response.body?.result?.data?.firstOrNull()
        Log.d(TAG, "getProgramName: programName=${program?.programName}")
        program?.programName
    }

    /**
     * Creates new samples on the server and returns the created samples (with assigned sampleDbIds).
     */
    suspend fun createSamples(samples: List<BrAPISample>): List<BrAPISample> =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "createSamples: posting ${samples.size} sample(s)")
            val response = samplesApi.samplesPost(samples)
            Log.d(TAG, "createSamples: response code=${response.statusCode}")
            val created = response.body?.result?.data ?: emptyList()
            Log.d(TAG, "createSamples: server returned ${created.size} created sample(s)")
            created
        }

    /**
     * Updates an existing sample on the server.
     * Uses the batch PUT endpoint.
     */
    suspend fun updateSample(sampleDbId: String, sample: BrAPISample): BrAPISample? =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "updateSample: sampleDbId=$sampleDbId, sampleName=${sample.sampleName}")
            val body = mapOf(sampleDbId to sample)
            val response = samplesApi.samplesPut(body)
            Log.d(TAG, "updateSample: response code=${response.statusCode}")
            val updated = response.body?.result?.data?.firstOrNull()
            Log.d(TAG, "updateSample: updated sampleDbId=${updated?.sampleDbId}")
            updated
        }
}
