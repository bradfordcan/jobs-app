package com.codev.assessment.jobsapp.ui.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.codev.assessment.jobsapp.data.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun JobListPreview() {
    val job = Job()
    JobsListScreen(arrayListOf(job, Job()), onRefresh = {

    })
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun JobsListScreen(jobs: List<Job>, onRefresh: () -> Unit) {
    // Pull to Refresh
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(500)
        onRefresh()
        refreshing = false
    }

    val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh)
    Scaffold(
        topBar = {

        },
        bottomBar = {

        },
        floatingActionButton = {

        },
        content = {
            Column(
                Modifier
                    .background(Color.White)
                    .fillMaxHeight()
                    .padding(it)
                    .windowInsetsPadding(
                        WindowInsets.ime
                    )
                    .navigationBarsPadding()
                    .background(Color.Transparent)
                    .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            ) {
                Box(Modifier.pullRefresh(pullRefreshState).padding(top = 0.dp)) {
                    Column() {
                        if (jobs.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .consumeWindowInsets(it)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                if (!refreshing) {
                                    itemsIndexed(jobs) { index, item ->
                                        JobItem(item)
                                    }
                                }
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun JobItemPreview() {
    val job = Job()
    JobItem(job)
}



@Composable
fun JobItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 14.dp, end = 14.dp)
            .clickable{ },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = job.title, fontWeight = FontWeight.Bold)
            Text(text = job.description)
            Spacer(Modifier.padding(8.dp))
            Text(text = "Openings: ${job.noOfOpenings}")
            Text(text = "Industry: ${job.industry}")
        }
    }
}