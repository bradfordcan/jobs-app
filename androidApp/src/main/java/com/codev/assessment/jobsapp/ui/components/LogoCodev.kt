package com.codev.assessment.jobsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codev.assessment.jobsapp.android.R

@Composable
fun LogoCoDev() =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.logocodev2),
            contentDescription = null,
            modifier = Modifier
                .width(110.dp)
                .clickable {

                }
        )
    }

@Composable
fun TopAppBarRow(content: (@Composable () -> Unit)) = Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .background(color = Color.White)
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(8.dp)
) {
    content()
}