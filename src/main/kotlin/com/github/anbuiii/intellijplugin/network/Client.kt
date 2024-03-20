package com.github.anbuiii.intellijplugin.network

import com.google.gson.annotations.SerializedName
import com.intellij.openapi.application.ApplicationListener
import com.intellij.util.application
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


class ApiController {
    val client by lazy {
        HttpClient() {
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    suspend fun askGemini(content: String): String {
        return try {
            val body = GeminiRequest(arrayListOf(Content(arrayListOf(Parts(content)))))
            val response =
                client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent") {
                    setBody(body)
                    parameter("key", "AIzaSyDWMjcBrh47GUFWmyptX85WsAw9vkUvzjQ")
                }
            val responseBuilder = StringBuilder()
            response.body<GeminiResponse>().candidates.forEach {
                it.content?.parts?.forEach {
                    responseBuilder.append(it.text)
                }
            }
            responseBuilder.toString()
        } catch (e: Exception) {
            e.message ?: "";
        }

    }
}


@Serializable
data class GeminiRequest(
    @SerializedName("contents") var contents: ArrayList<Content> = arrayListOf()
)

@Serializable
data class GeminiResponse(
    @SerializedName("candidates")
    var candidates: ArrayList<Candidates> = arrayListOf(),

    @SerializedName("promptFeedback")
    var promptFeedback: PromptFeedback? = PromptFeedback()
)

@Serializable
data class Parts(
    @SerializedName("text")
    var text: String? = null
)

@Serializable
data class Content(
    @SerializedName("parts")
    var parts: ArrayList<Parts> = arrayListOf(),

    @SerializedName("role")
    var role: String? = null
)

@Serializable
data class SafetyRatings(

    @SerializedName("category") var category: String? = null,
    @SerializedName("probability") var probability: String? = null

)

@Serializable
data class Candidates(

    @SerializedName("content") var content: Content? = Content(),
    @SerializedName("finishReason") var finishReason: String? = null,
    @SerializedName("index") var index: Int? = null,
    @SerializedName("safetyRatings") var safetyRatings: ArrayList<SafetyRatings> = arrayListOf()

)

@Serializable
data class PromptFeedback(
    @SerializedName("safetyRatings") var safetyRatings: ArrayList<SafetyRatings> = arrayListOf()

)