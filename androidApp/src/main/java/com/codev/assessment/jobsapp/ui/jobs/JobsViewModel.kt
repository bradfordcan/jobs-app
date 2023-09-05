package com.codev.assessment.jobsapp.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.body.NewJobRequest
import com.codev.assessment.jobsapp.repository.JobsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JobsListState(
    var jobs: List<Job> = emptyList(),
)

data class NewJobState(
    var jobId: String = "",
)

class JobsViewModel(
    private val repository: JobsDataSource,
) : ViewModel() {
    private val logger = Logger.withTag("JobsApp:JobsViewModel")

    private val _jobsListState = MutableStateFlow(JobsListState())
    val jobsListState: StateFlow<JobsListState> = _jobsListState
    fun getJobs() {
        viewModelScope.launch {
            repository.getAll().collect {
                _jobsListState.value = JobsListState(jobs = it)
            }
        }
    }

    private val _newJobId = MutableStateFlow(NewJobState())
    val newJobId: StateFlow<NewJobState> = _newJobId
    fun insertJob(newJobRequest: NewJobRequest) {
        viewModelScope.launch {
            repository.insertJob(newJobRequest).collect {
                _newJobId.value = NewJobState(jobId = it)
            }
        }
    }
}