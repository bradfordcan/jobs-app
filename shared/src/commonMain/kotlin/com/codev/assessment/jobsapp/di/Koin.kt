package com.codev.assessment.jobsapp.di

import com.codev.assessment.jobsapp.remote.ApplicantsApi
import com.codev.assessment.jobsapp.remote.JobApplicantApi
import com.codev.assessment.jobsapp.remote.JobsApi
import com.codev.assessment.jobsapp.repository.ApplicantsDataSource
import com.codev.assessment.jobsapp.repository.ApplicantsRepository
import com.codev.assessment.jobsapp.repository.JobApplicantDataSource
import com.codev.assessment.jobsapp.repository.JobApplicantRepository
import com.codev.assessment.jobsapp.repository.JobsDataSource
import com.codev.assessment.jobsapp.repository.JobsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<JobsDataSource> { JobsRepository() }
    single<ApplicantsDataSource> { ApplicantsRepository() }
    single<JobApplicantDataSource> { JobApplicantRepository() }

    single { JobsApi(get()) }
    single { ApplicantsApi(get()) }
    single { JobApplicantApi(get()) }

    /*single {
        DatabaseHelper(
            get(),
            getWith("DatabaseHelper"),
            Dispatchers.Default
        )
    }*/
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    encodeDefaults = true
    classDiscriminator = "#class"
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean,
): HttpClient {
    val httpClient = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = io.ktor.client.plugins.logging.Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    return httpClient
}
