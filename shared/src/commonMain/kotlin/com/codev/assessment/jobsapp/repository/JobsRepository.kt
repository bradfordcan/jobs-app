package com.codev.assessment.jobsapp.repository

import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.JobsApi
import com.codev.assessment.jobsapp.remote.body.NewJobRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


interface JobsDataSource {
    fun getAll(): Flow<List<Job>>

    fun insertJob(newJobRequest: NewJobRequest): Flow<String>

}

class JobsRepository : KoinComponent, JobsDataSource {
    private val api: JobsApi by inject()
    // private val logger = Logger.withTag("JobsApp:JobsRepository")

    override fun getAll(): Flow<List<Job>> {
        return flow {
            val jobs = api.getAll()
            emit(jobs)
        }
    }

    override fun insertJob(newJobRequest: NewJobRequest): Flow<String> {
        return flow {
            val successfulInsert = api.insertJob(newJobRequest)
            emit(successfulInsert)
        }
    }

}