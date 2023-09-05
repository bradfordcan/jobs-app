package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.data.Job
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class JobsApi(
    private val client: HttpClient,
    var baseUrl: String = "${Url.BASE_URL}/Job",
) : KoinComponent {

    suspend fun getAll() = client.get(baseUrl).body<Response<List<Job>>>()

}