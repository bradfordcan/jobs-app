package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.data.Job
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent

private const val BASE_URL = "https://codev-job-board-app.azurewebsites.net/api/JobApplicant"

class JobApplicantApi(
    private val client: HttpClient,
) : KoinComponent {
    suspend fun applyJob(jobId: String, applicant: Applicant): String {
        val httpResponse: HttpResponse = client.post("$BASE_URL/applyjob/$jobId") {
            contentType(ContentType.Application.Json)
            setBody(applicant)
        }
        return when (httpResponse.status.value) {
            in 200..299 -> {
                "success"
            }

            400 -> {
                httpResponse.body()
            }

            else -> {
                ""
            }
        }
    }

    suspend fun getJobsApplied(userId: String): List<Job> {
        val httpResponse: HttpResponse = client.get("$BASE_URL/getjobsapplied/$userId")
        return when (httpResponse.status.value) {
            in 200..299 -> {
                httpResponse.body()
            }

            else -> {
                emptyList()
            }
        }
    }
}