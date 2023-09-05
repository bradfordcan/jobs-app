package com.codev.assessment.jobsapp.di

import com.codev.assessment.jobsapp.remote.JobsApi
import com.codev.assessment.jobsapp.repository.JobsDataSource
import com.codev.assessment.jobsapp.repository.JobsRepository
import com.codev.assessment.jobsapp.repository.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
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
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }


fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    /*val baseLogger = Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "ComSpace")
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }*/

    single { JobsApi(get()) }
    // single { ApplicantsApi(get()) }
    // single { JobApplicantApi(get()) }

    single<JobsDataSource> { JobsRepository() }
    // single<ApplicantsDataSource> { ApplicantsRepository() }
    // single<JobApplicantDataSource> { JobApplicantRepository() }


    /*single {
        DatabaseHelper(
            get(),
            getWith("DatabaseHelper"),
            Dispatchers.Default
        )
    }*/
}

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}