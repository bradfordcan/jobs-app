package com.codev.assessment.jobsapp.android

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.ui.components.AppMainBackground
import com.codev.assessment.jobsapp.ui.components.ApplicantDialog
import com.codev.assessment.jobsapp.ui.components.ChangeUserTypeDialog
import com.codev.assessment.jobsapp.ui.components.SimpleAlertDialog
import com.codev.assessment.jobsapp.ui.dialog.UiViewModel
import com.codev.assessment.jobsapp.ui.jobs.JobsListScreen
import com.codev.assessment.jobsapp.ui.jobs.JobsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings: SharedPreferences by inject()
        var defaultSelected = settings.getInt("userType", 0)
        val jobsViewModel: JobsViewModel by viewModel()
        val uiViewModel: UiViewModel by viewModel()

        val userTypes = arrayListOf("Admin", "Applicant")

        setContent {
            AppMainBackground {
                val context = LocalContext.current

                val searchJobQuery = remember { mutableStateOf("") }
                val industryTypeQuery = remember { mutableIntStateOf(0) }
                fun filter() {
                    jobsViewModel.filterJobsByIndustry(searchJobQuery.value, industryTypeQuery.value)
                }

                val userType = remember { mutableStateOf(userTypes[settings.getInt("userType", 0)]) }
                val jobs = remember { mutableListOf<Job>() }
                val jobsListState by jobsViewModel.jobsListState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = jobsListState.jobs) {
                    jobs.clear()
                    if (jobsListState.jobs.isNotEmpty()) {
                        jobs.addAll(jobsListState.jobs)
                        jobs.sortByDescending { it.title }
                    }
                }

                val filteredJobsListState by jobsViewModel.filteredJobsListState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = filteredJobsListState.filteredJobs) {
                    jobs.clear()
                    if (filteredJobsListState.filteredJobs.isNotEmpty()) {
                        jobs.addAll(filteredJobsListState.filteredJobs)
                        jobs.sortByDescending { it.title }
                    }
                }

                val newJob by jobsViewModel.newJobId.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = newJob.jobId) {
                    if (newJob.jobId.isNotEmpty()) {
                        jobs.clear()
                        showToast(context, "Job opening added!")
                        jobsViewModel.getJobs() // refresh list with newly added job
                    }
                }

                val updateJob by jobsViewModel.updateJobState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = updateJob.successUpdate) {
                    if (updateJob.successUpdate) {
                        jobs.clear()
                        showToast(context, "Job updated!")
                        jobsViewModel.getJobs() // refresh list with updated job
                    }
                }

                val deleteJob by jobsViewModel.deleteJobState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = deleteJob.successDelete) {
                    if (deleteJob.successDelete) {
                        jobs.clear()
                        showToast(context, "Job deleted!")
                        jobsViewModel.getJobs() // refresh list
                    }
                }

                // called on first run
                jobsViewModel.getJobs()

                // delete confirmation dialog
                val confirmDeleteJobDialogState by uiViewModel.showAlertDialog.collectAsState()
                SimpleAlertDialog(
                    show = confirmDeleteJobDialogState.showAlertDialog,
                    hideCancel = false,
                    onDismiss = {
                        uiViewModel.onAlertDialogDismissed()
                    },
                    onConfirm = {
                        uiViewModel.onAlertDialogDismissed()
                        jobsViewModel.deleteJob(confirmDeleteJobDialogState.data)
                    },
                    title = confirmDeleteJobDialogState.title,
                    message = confirmDeleteJobDialogState.message
                )

                // apply job dialog
                val applyJobDialogState by uiViewModel.showApplyJobDialog.collectAsState()
                if(applyJobDialogState.data != null) {
                    ApplicantDialog(title = applyJobDialogState.data!!.title, onDismiss = { fullName, email ->
                        if (fullName.isNotEmpty() && email.isNotEmpty()) {

                        } else {
                            showToast(context, "Make sure fullName and email is not empty")
                        }
                    })
                }

                /*SimpleAlertDialog(
                    show = applyJobDialogState.showAlertDialog,
                    hideCancel = false,
                    onDismiss = {
                        uiViewModel.onApplyJobDialogDismissed()
                    },
                    onConfirm = {
                        uiViewModel.onApplyJobDialogDismissed()

                    },
                    title = applyJobDialogState.title,
                    message = applyJobDialogState.message
                )*/

                val userTypeDialog = remember { mutableStateOf(true) }

                if (userTypeDialog.value) {
                    defaultSelected = settings.getInt("userType", 0)
                    ChangeUserTypeDialog(title = "Select User Type",
                        optionsList = userTypes,
                        defaultSelected = defaultSelected,
                        submitButtonText = "Apply Settings",
                        onSubmitButtonClick = {
                            settings.edit().putInt("userType", it).apply()
                            userType.value = userTypes[it]
                            showToast(context, "You are now an ${userTypes[it]}")
                        },
                        onDismissRequest = { userTypeDialog.value = false })
                }

                JobsListScreen(
                    authorized = userType.value.contentEquals("Admin"),
                    jobs = jobs,
                    onRefresh = {
                        // Get all jobs
                        jobsViewModel.getJobs()
                    },
                    onCreateNewJob = {
                        jobsViewModel.insertJob(it)
                    },
                    onUpdateJob = {
                        jobsViewModel.updateJob(it)
                    },
                    onDeleteJob = {
                        uiViewModel.onOpenAlertDialogClicked(
                            it,
                            "Confirm Delete",
                            "Are you sure you want to delete this job?"
                        )
                    },
                    onSearchJob = {
                        if (it.isNotEmpty()) {
                            searchJobQuery.value = it
                            filter()
                        }
                    },
                    onChangeUserType = {
                        userTypeDialog.value = true
                    }, onDisplayAll = {
                        // Get all jobs
                        jobsViewModel.reset()
                        industryTypeQuery.value = -1
                        jobsViewModel.getJobs()
                    }, onFilterByIndustry = {
                        industryTypeQuery.value = it
                        filter()
                    }, onApplyJob = {
                        uiViewModel.onOpenApplyJobDialogClicked(
                            it,
                            "Apply as ${it.title}",
                            "Are you sure you want to apply for this job?"
                        )
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
