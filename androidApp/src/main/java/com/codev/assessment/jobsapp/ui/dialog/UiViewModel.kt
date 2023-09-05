package com.codev.assessment.jobsapp.ui.dialog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
data class AlertDialogState(
    val data: String = "",
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
}