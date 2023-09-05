package com.codev.assessment.jobsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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

@Composable
fun ChangeUserTypeDialog(
    title: String,
    optionsList: List<String>,
    defaultSelected: Int,
    submitButtonText: String,
    onSubmitButtonClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val selectedOption = remember { mutableIntStateOf(defaultSelected) }
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.width(360.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    itemsIndexed(optionsList) { index, item ->
                        CustomRadioButton(
                            item,
                            optionsList[selectedOption.value]
                        ) { selectedValue ->
                            selectedOption.value = optionsList.indexOf(selectedValue)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onSubmitButtonClick.invoke(selectedOption.value)
                        onDismissRequest.invoke()
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = submitButtonText)
                }
            }

        }
    }
}

@Composable
fun CustomRadioButton(text: String, selectedValue: String, onClickListener: (String) -> Unit) {
    Row(Modifier
        .fillMaxWidth()
        .selectable(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            }
        ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}