package com.codev.assessment.jobsapp.di

import android.util.Log
import com.codev.assessment.jobsapp.ui.dialog.UiViewModel
import com.codev.assessment.jobsapp.ui.jobs.JobsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { JobsViewModel(get()) }
    viewModel { UiViewModel() }

    // logger
    single {
        { Log.i("Startup", "Hello from Android/Kotlin!") }
    }
}