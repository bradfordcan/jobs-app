package com.codev.assessment.jobsapp.remote

import com.codev.assessment.jobsapp.remote.Url.BASE_URL
import io.ktor.client.HttpClient
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
    data class Ok<out T>(val success: Boolean? = false, val data: T? = null, val message: String) :
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

class ApplicantsApi(
    private val client: HttpClient,
    var baseUrl: String = "$BASE_URL/Applicant",
) : KoinComponent {

}