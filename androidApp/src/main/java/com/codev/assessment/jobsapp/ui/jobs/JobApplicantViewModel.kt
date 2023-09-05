package com.codev.assessment.jobsapp.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.repository.JobApplicantDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserJobsListState(
    var jobs: List<Job> = emptyList(),
)

data class ApplyJobState(
    var result: String = "",
)

class JobApplicantViewModel(
    private val repository: JobApplicantDataSource,
) : ViewModel() {
    private val _userJobsListState = MutableStateFlow(UserJobsListState())
    val userJobsListState: StateFlow<UserJobsListState> = _userJobsListState
    fun getJobsApplied(userId: String) {
        viewModelScope.launch {
            repository.getJobsApplied(userId).collect {
                _userJobsListState.value = UserJobsListState(jobs = it)
            }
        }
    }


    private val _applyJobState = MutableStateFlow(ApplyJobState())
    val applyJobState: StateFlow<ApplyJobState> = _applyJobState
    fun applyJob(jobId: String, applicant: Applicant) {
        viewModelScope.launch {
            repository.applyJob(jobId, applicant).collect {
                _applyJobState.value = ApplyJobState(result = it)
            }
        }
    }

}