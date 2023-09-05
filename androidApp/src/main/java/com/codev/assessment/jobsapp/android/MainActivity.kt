package com.codev.assessment.jobsapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.ui.components.AppMainBackground
import com.codev.assessment.jobsapp.ui.jobs.JobsListScreen
import com.codev.assessment.jobsapp.ui.jobs.JobsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jobsViewModel: JobsViewModel by viewModel()
        setContent {
            AppMainBackground {
                val jobs = remember { mutableListOf<Job>() }
                val jobsListState by jobsViewModel.jobsListState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = jobsListState.jobs) {
                    jobs.clear()
                    if (jobsListState.jobs.isNotEmpty()) {
                        jobs.addAll(jobsListState.jobs)
                        jobs.sortByDescending { it.title }
                        // todo: do basic filtering
                    }
                }
                jobsViewModel.getJobs()
                JobsListScreen(
                    jobs = jobs,
                    onRefresh = {
                        // Get all jobs
                        jobsViewModel.getJobs()
                    },
                    onCreateNewJob = {

                    },
                    onUpdateJob = {

                    }
                )
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
