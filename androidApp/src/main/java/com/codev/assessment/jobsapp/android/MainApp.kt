package com.codev.assessment.jobsapp.android

import android.app.Application
import com.codev.assessment.jobsapp.di.appModule
import com.codev.assessment.jobsapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApp)
            // Load modules
            modules(appModule)
        }
    }
}

