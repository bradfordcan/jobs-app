package com.codev.assessment.jobsapp.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SimpleAlertDialog(
    show: Boolean,
    hideCancel: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Please confirm",
    message: String = "You are about to delete a job.  Click Ok to proceed",
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            dismissButton = {
                if (!hideCancel) {
                    TextButton(onClick = onDismiss)
                    { Text(text = "Cancel") }
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            })
    }
}