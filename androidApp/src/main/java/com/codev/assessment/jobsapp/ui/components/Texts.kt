package com.codev.assessment.jobsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun CClickableText(text: String, onClick: () -> Unit) =
    Text(text = text, color = Color.Black, modifier = Modifier.clickable(onClick = {
        onClick()
    }), fontSize = 20.sp)