package com.codev.assessment.jobsapp.ui.dialog

import androidx.lifecycle.ViewModel
import com.codev.assessment.jobsapp.data.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
data class AlertDialogState(
    val data: String = "",
    val title: String = "",
    val message: String = "",
    val showAlertDialog: Boolean = false,
)

data class ApplyJobDialogState(
    val data: Job? = null,
    val title: String = "",
    val message: String = "",
    val showAlertDialog: Boolean = false,
)
class UiViewModel: ViewModel() {
    private val _showAlertDialog = MutableStateFlow(AlertDialogState())
    val showAlertDialog: StateFlow<AlertDialogState> = _showAlertDialog.asStateFlow()

    fun onOpenAlertDialogClicked(data: String, title: String, message: String) {
        _showAlertDialog.value = AlertDialogState(data, title, message, true)
    }

    fun onConfirmAlertDialogClicked() {
        _showAlertDialog.value = AlertDialogState("","", "", false)
    }

    fun onAlertDialogDismissed() {
        _showAlertDialog.value = AlertDialogState("","", "", false)
    }

    private val _showApplyJobDialog = MutableStateFlow(ApplyJobDialogState())
    val showApplyJobDialog: StateFlow<ApplyJobDialogState> = _showApplyJobDialog.asStateFlow()

    fun onOpenApplyJobDialogClicked(data: Job, title: String, message: String) {
        _showApplyJobDialog.value = ApplyJobDialogState(data, title, message, true)
    }

    fun onConfirmApplyJobDialogClicked() {
        _showApplyJobDialog.value = ApplyJobDialogState(null,"", "", false)
    }

    fun onApplyJobDialogDismissed() {
        _showApplyJobDialog.value = ApplyJobDialogState(null,"", "", false)
    }
}