package com.codev.assessment.jobsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowInput(padding: Dp = 0.dp, rowInputLayout: @Composable () -> Unit) = Column(
    modifier = Modifier.padding(
        top = 0.dp,
        start = padding,
        end = padding,
        bottom = padding
    )
) {
    rowInputLayout()
}