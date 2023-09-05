package com.codev.assessment.jobsapp.repository

import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.remote.ApplicantsApi
import com.codev.assessment.jobsapp.remote.body.ApplicantRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ApplicantsDataSource {
    fun getAll(): Flow<List<Applicant>>

    fun insertApplicant(request: ApplicantRequest): Flow<String>

    fun updateApplicant(applicant: Applicant): Flow<Boolean>

    fun deleteApplicant(id: String): Flow<Boolean>
}

class ApplicantsRepository : KoinComponent, ApplicantsDataSource {
    private val api: ApplicantsApi by inject()
    // private val logger = Logger.withTag("JobsApp:JobsRepository")

    override fun getAll(): Flow<List<Applicant>> {
        return flow {
            val applicants = api.getAll()
            emit(applicants)
        }
    }

    override fun insertApplicant(request: ApplicantRequest): Flow<String> {
        return flow {
            val successfulInsert = api.insertApplicant(request)
            emit(successfulInsert)
        }
    }

    override fun updateApplicant(applicant: Applicant): Flow<Boolean> {
        return flow {
            val successfulUpdate = api.updateApplicant(applicant)
            emit(successfulUpdate)
        }
    }

    override fun deleteApplicant(id: String): Flow<Boolean> {
        return flow {
            val successfulDelete = api.deleteApplicant(id)
            emit(successfulDelete)
        }
    }
}