package com.codev.assessment.jobsapp.repository

import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.JobsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


interface JobsDataSource {
    fun getAll(): Flow<List<Job>>
}

class JobsRepository : KoinComponent, JobsDataSource {
    private val api: JobsApi by inject()
    // private val logger = Logger.withTag("JobsApp:JobsRepository")

    override fun getAll(): Flow<List<Job>> {
        return flow {
            /*when (val x = api.getAll()) {
                is Response.Ok -> {
                    val jobs = x.data as List<Job>
                    // logger.d("jobs ${jobs.map { it.id }}")
                    emit(jobs)
                }

                is Response.Error -> {

                }

                is Response.ApiError -> {

                }
            }*/
            val jobs = api.getAll()
            emit(jobs)
        }
    }

}