package com.codev.assessment.jobsapp.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) =
    Button(modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(text = text, color = Color.White)
    }