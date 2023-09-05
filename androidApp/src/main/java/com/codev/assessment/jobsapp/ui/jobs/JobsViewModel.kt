package com.codev.assessment.jobsapp.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.repository.JobsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JobsListState(
    var jobs: List<Job> = emptyList(),
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
}