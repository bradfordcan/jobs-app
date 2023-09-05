package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.data.Applicant
import com.codev.assessment.jobsapp.remote.body.ApplicantRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

@Serializable(with = ResponseSerializer::class)
sealed class Response<out T> {
    @Serializable
    data class Ok<out T>(val data: T? = null) :
        Response<T>()

    @Serializable
    data class Error(val message: String) : Response<Nothing>()

    @Serializable
    data class ApiError(val detail: String) : Response<Nothing>()
}

@Suppress("UNCHECKED_CAST")
private class ResponseSerializer<T>(private val dataSerializer: KSerializer<T>) :
    JsonContentPolymorphicSerializer<Response<T>>(Response::class as KClass<Response<T>>) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out Response<T>> =
        when {
            Response.Ok<T>::data.name in element.jsonObject -> Response.Ok.serializer(dataSerializer)
            // Response.Error::message.name in element.jsonObject -> Response.Error.serializer()
            // else -> throw SerializationException("Cannot determine the type to deserialize: $element")
            else -> Response.ApiError.serializer()
        }
}

private const val BASE_URL = "https://codev-job-board-app.azurewebsites.net/api/Applicant"

class ApplicantsApi(
    private val client: HttpClient,
) : KoinComponent {
    suspend fun getAll(): List<Applicant> {
        val httpResponse: HttpResponse = client.get("$BASE_URL/getall")
        return if (httpResponse.status.value in 200..299) {
            httpResponse.body()
        } else {
            emptyList()
        }
    }

    suspend fun insertApplicant(request: ApplicantRequest): String {
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

    suspend fun updateApplicant(request: Applicant): Boolean {
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

    suspend fun deleteApplicant(id: String): Boolean {
        val httpResponse: HttpResponse = client.delete("$BASE_URL/delete") {
            contentType(ContentType.Application.Json)
            parameter("id", id)
        }
        return httpResponse.status.value in 200..299
    }
}