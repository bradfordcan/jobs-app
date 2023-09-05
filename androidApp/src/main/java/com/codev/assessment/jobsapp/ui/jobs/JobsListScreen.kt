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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.body.NewJobRequest
import com.codev.assessment.jobsapp.ui.components.CClickableText
import com.codev.assessment.jobsapp.ui.components.CEditTextLabel
import com.codev.assessment.jobsapp.ui.components.DropDownMenu
import com.codev.assessment.jobsapp.ui.components.EditTextSingleLineBordered
import com.codev.assessment.jobsapp.ui.components.LogoCoDev
import com.codev.assessment.jobsapp.ui.components.PrimaryButton
import com.codev.assessment.jobsapp.ui.components.RowInput
import com.codev.assessment.jobsapp.ui.components.TopAppBarRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun JobListPreview() {
    val job = Job()
    JobsListScreen(arrayListOf(job, Job()),
        onRefresh = {

        }, onCreateNewJob = {

        }, onUpdateJob = {

        })
}

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun JobsListScreen(
    jobs: List<Job>,
    onRefresh: () -> Unit,
    onCreateNewJob: (NewJobRequest) -> Unit,
    onUpdateJob: (Job) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

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

    // create/update job
    val jobTitle = remember { mutableStateOf("") }
    val jobDescription = remember { mutableStateOf("") }
    val numOpenings = remember { mutableIntStateOf(0) }
    val industry = remember { mutableIntStateOf(0) }
    val newJob = remember { mutableStateOf(NewJobRequest()) }
    val updateJob = remember { mutableStateOf(Job()) }
    val edit = remember { mutableStateOf(false) }
    val editScope = rememberCoroutineScope()

    fun toggleEditMode(isEdit: Boolean) = editScope.launch {
        edit.value = isEdit
        if (!edit.value) { // Create
            newJob.value = NewJobRequest()
        }
    }

    // state for create/update job modal
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    LaunchedEffect(modalSheetState.targetValue) {
        if (modalSheetState.targetValue == ModalBottomSheetValue.Hidden) {
            toggleEditMode(false)
        }
    }

    val clearScope = rememberCoroutineScope()
    fun clear() = clearScope.launch {

    }

    // create/update job modal
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            Scaffold(
                topBar = {
                    TopAppBarRow {
                        CClickableText(text = "Cancel") {
                            coroutineScope.launch { modalSheetState.hide() }
                            keyboardController?.hide()
                            clear()
                            toggleEditMode(false)
                        }

                        val postText = if (edit.value) "Update" else "Create Job"
                        PrimaryButton(text = postText) {
                            coroutineScope.launch { modalSheetState.hide() }
                            keyboardController?.hide()
                            clear()

                            if (!edit.value) {
                                newJob.value.title = jobTitle.value
                                newJob.value.description = jobDescription.value
                                newJob.value.noOfOpenings = numOpenings.value
                                newJob.value.industry = industry.value
                                onCreateNewJob(newJob.value)
                            } else {
                                onUpdateJob(updateJob.value)
                            }
                        }
                    }
                },
                bottomBar = {

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
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(0.9f)
                                .padding(
                                    top = 8.dp,
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 0.dp
                                )
                        ) {
                            if (edit.value) {
                                jobTitle.value = updateJob.value.title
                            }
                            RowInput(padding = 16.dp) {
                                CEditTextLabel(text = "First Name")
                                EditTextSingleLineBordered(
                                    LocalFocusManager.current,
                                    value = jobTitle.value,
                                    hint = "Job Title",
                                    onValueUpdate = { title ->
                                        jobTitle.value = title
                                    }
                                )
                            }

                            if (edit.value) {
                                jobDescription.value = updateJob.value.description
                            }
                            RowInput(padding = 16.dp) {
                                CEditTextLabel(text = "Job Description")
                                EditTextSingleLineBordered(
                                    LocalFocusManager.current,
                                    value = jobDescription.value,
                                    hint = "Job Description",
                                    onValueUpdate = { description ->
                                        jobDescription.value = description
                                    }
                                )
                            }

                            if (edit.value) {
                                numOpenings.intValue = updateJob.value.noOfOpenings
                            }
                            RowInput(padding = 16.dp) {
                                CEditTextLabel(text = "No. of Openings", paddingBottom = 0.dp)
                                EditTextSingleLineBordered(
                                    LocalFocusManager.current,
                                    value = numOpenings.intValue.toString(),
                                    hint = "No. of Openings",
                                    onValueUpdate = { selectedNumOpenings ->
                                        numOpenings.intValue = if(selectedNumOpenings.isNotEmpty()) {
                                            selectedNumOpenings.toInt()
                                        } else {
                                            0
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }


                            if (edit.value) {
                                industry.intValue = updateJob.value.industry
                            }
                            val industryTypes = (0..8).toList().map { it.toString() }
                            RowInput(padding = 16.dp) {
                                CEditTextLabel(text = "Industry Type", isRequired = true)
                                DropDownMenu(
                                    industryTypes,
                                    valueText = industry.intValue.toString(),
                                    onValueUpdate = { type ->
                                        industry.intValue = type.toInt()
                                    },
                                    "Industry Type"
                                )
                            }
                        }
                    }
                }
            )
        }
    ) {
        // jobs list
        Scaffold(
            topBar = {
                TopAppBarRow {
                    LogoCoDev()
                }
            },
            bottomBar = {

            },
            floatingActionButton = {
                Box(
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else {
                                    modalSheetState.show()
                                }
                                toggleEditMode(false)
                            }
                        },
                        backgroundColor = Color.Red
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = Color.White,
                        )
                    }
                }
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
                    Box(
                        Modifier
                            .pullRefresh(pullRefreshState)
                            .padding(top = 0.dp)
                    ) {
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
                                            JobItem(item, onEdit = {
                                                coroutineScope.launch { modalSheetState.show() }
                                                updateJob.value.id = item.id
                                                updateJob.value.title = item.title
                                                updateJob.value.description = item.description
                                                updateJob.value.noOfOpenings = item.noOfOpenings
                                                updateJob.value.industry = item.industry
                                                toggleEditMode(true)
                                            })
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
}


@Preview(showBackground = true)
@Composable
fun JobItemPreview() {
    val job = Job()
    JobItem(job, onEdit = {

    })
}


@Composable
fun JobItem(job: Job, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 14.dp, end = 14.dp)
            .clickable {
                onEdit()
            },
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
            Text(text = "${job.title} (${job.noOfOpenings})", fontWeight = FontWeight.Bold)
            Text(text = job.description)
            Spacer(Modifier.padding(2.dp))
            Text(text = "Industry: ${job.industry}", color = Color.LightGray)
        }
    }
}