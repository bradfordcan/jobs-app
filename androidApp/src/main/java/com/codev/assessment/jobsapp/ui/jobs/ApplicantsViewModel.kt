package com.codev.assessment.jobsapp.ui.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.remote.body.ApplicantRequest
import com.codev.assessment.jobsapp.repository.ApplicantsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ApplicantsListState(
    var applicants: List<Applicant> = emptyList(),
    val jobId: String,
    val fullName: String,
    val email: String,
)

data class NewApplicantState(
    var applicantId: String = "",
    val jobId: String,
    val fullName: String,
    val email: String,
)

data class UpdateApplicantState(
    var successUpdate: Boolean = false,
)

data class DeleteApplicantState(
    var successDelete: Boolean = false,
)

class ApplicantsViewModel(
    private val repository: ApplicantsDataSource,
) : ViewModel() {

    private val _applicantsListState = MutableStateFlow(ApplicantsListState(emptyList(),"","",""))
    val applicantsListState: StateFlow<ApplicantsListState> = _applicantsListState
    fun getApplicants(jobId: String, fullName: String, email: String) {
        viewModelScope.launch {
            repository.getAll().collect {
                _applicantsListState.value = ApplicantsListState(applicants = it, jobId = jobId, fullName = fullName, email = email)
            }
        }
    }

    private val _newApplicantId = MutableStateFlow(NewApplicantState("", "", "", ""))
    val newApplicantId: StateFlow<NewApplicantState> = _newApplicantId
    fun insertApplicant(jobId: String, request: ApplicantRequest) {
        viewModelScope.launch {
            repository.insertApplicant(request).collect {
                _newApplicantId.value = NewApplicantState(applicantId = it, jobId = jobId, fullName = request.fullName, email = request.emailAddress)
            }
        }
    }

    private val _updateApplicantState = MutableStateFlow(UpdateApplicantState())
    val updateApplicantState: StateFlow<UpdateApplicantState> = _updateApplicantState
    fun updateApplicant(applicant: Applicant) {
        viewModelScope.launch {
            repository.updateApplicant(applicant).collect {
                _updateApplicantState.value = UpdateApplicantState(successUpdate = it)
            }
        }
    }

    private val _deleteApplicantState = MutableStateFlow(DeleteApplicantState())
    val deleteApplicantState: StateFlow<DeleteApplicantState> = _deleteApplicantState
    fun deleteApplicant(id: String) {
        viewModelScope.launch {
            repository.deleteApplicant(id).collect {
                _deleteApplicantState.value = DeleteApplicantState(successDelete = it)
            }
        }
    }

}