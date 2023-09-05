package com.codev.assessment.jobsapp.repository

import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.JobApplicantApi
import com.codev.assessment.jobsapp.remote.JobsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface JobApplicantDataSource {
    fun applyJob(jobId: String, applicant: Applicant): Flow<String> // returns 400 if already applied, display body if ever

    fun getJobsApplied(id: String): Flow<List<Job>>

}

class JobApplicantRepository : KoinComponent, JobApplicantDataSource {
    private val api: JobApplicantApi by inject()
    override fun applyJob(jobId: String, applicant: Applicant): Flow<String> {
        return flow {
            val response = api.applyJob(jobId, applicant)
            emit(response)
        }
    }

    override fun getJobsApplied(id: String): Flow<List<Job>> {
        return flow {
            val jobs = api.getJobsApplied(id)
            emit(jobs)
        }
    }

}