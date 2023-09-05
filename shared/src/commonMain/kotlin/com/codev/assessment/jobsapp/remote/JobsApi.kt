package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.data.Job
import com.codev.assessment.jobsapp.remote.body.NewJobRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.AttributeKey
import org.koin.core.component.KoinComponent

private const val BASE_URL = "https://codev-job-board-app.azurewebsites.net/api/Job"
class JobsApi(
    private val client: HttpClient
) : KoinComponent {

    suspend fun getAll(): List<Job> {
        val httpResponse: HttpResponse = client.get("$BASE_URL/getall")
        return if (httpResponse.status.value in 200..299) {
            httpResponse.body()
        } else {
            emptyList()
        }
    }

    suspend fun filterJobs(query: String, industryType: Int): List<Job> {
        val httpResponse: HttpResponse = client.get("$BASE_URL/filter") {
            if(query.isNotEmpty())
                parameter("keyword", query)

            if(industryType != -1)
                parameter("jobIndustryType", industryType)
        }
        return if (httpResponse.status.value in 200..299) {
            httpResponse.body()
        } else {
            emptyList()
        }
    }

    suspend fun insertJob(request: NewJobRequest): String {
        val httpResponse: HttpResponse = client.post("$BASE_URL/insert") {
            contentType(ContentType.Application.Json)
            setBody(body = request)
        }
        return if (httpResponse.status.value in 200..299) {
            httpResponse.body()
        } else {
            ""
        }
    }

    suspend fun updateJob(request: Job): Boolean {
        val httpResponse: HttpResponse = client.put("$BASE_URL/update") {
            contentType(ContentType.Application.Json)
            setBody(body = request)
        }
        return if (httpResponse.status.value in 200..299) {
            httpResponse.body()
        } else {
            false
        }
    }

    suspend fun deleteJob(id: String): Boolean {
        val httpResponse: HttpResponse = client.delete("$BASE_URL/delete") {
            contentType(ContentType.Application.Json)
            parameter("id", id)
        }
        return httpResponse.status.value in 200..299
    }

}