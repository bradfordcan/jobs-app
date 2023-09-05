package com.codev.assessment.jobsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.codev.assessment.jobsapp.android.showToast

@Composable
fun ApplicantDialog(title: String, onDismiss: (String, String) -> Unit) {
    val context = LocalContext.current
    var fullName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = {
        if (fullName.isNotEmpty() && email.isNotEmpty()) {
            onDismiss(fullName, email)
        } else {
            showToast(context, "Make sure fullName and email is not empty")
        }
    }) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                Modifier
                    .background(Color.White)
            ) {

                Text(
                    text = "Apply as $title",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it }, modifier = Modifier.padding(8.dp),
                    label = { Text("Full Name") }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it }, modifier = Modifier.padding(8.dp),
                    label = { Text("Email") }
                )

                Row {
                    OutlinedButton(
                        onClick = {

                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }


                    Button(
                        onClick = {
                            onDismiss(fullName, email)
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Apply Job")
                    }
                }
            }
        }
    }
}