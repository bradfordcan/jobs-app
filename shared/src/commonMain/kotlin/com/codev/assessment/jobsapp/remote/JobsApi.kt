package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.data.Job
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

private const val BASE_URL = "https://codev-job-board-app.azurewebsites.net/api/Job"
class JobsApi(
    private val client: HttpClient
) : KoinComponent {

    suspend fun getAll() = client.get("$BASE_URL/getall").body<List<Job>>()

}