package com.codev.assessment.jobsapp.remote

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent

class JobApplicantApi(
    private val client: HttpClient,
    var baseUrl: String = "${Url.BASE_URL}/JobApplicant",
) : KoinComponent {

}