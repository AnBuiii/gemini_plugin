package com.github.anbuiii.intellijplugin.network

import com.github.anbuiii.intellijplugin.network.model.Content
import com.github.anbuiii.intellijplugin.network.model.GeminiRequest
import com.github.anbuiii.intellijplugin.network.model.GeminiResponse
import com.github.anbuiii.intellijplugin.network.model.Parts
import com.github.anbuiii.intellijplugin.setting.AppSettingsState
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ApiController {

    private fun generateRequestBody(value: String): GeminiRequest {
        return GeminiRequest(arrayListOf(Content(arrayListOf(Parts(value)))))
    }

    private val client by lazy {
        HttpClient(CIO) {
            install(DefaultRequest) {

            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun askGemini2(content: String): String {
        return try {
            val body = generateRequestBody(content)

            val bodyContent = Json.encodeToString(body)
            val response =
                client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent") {
                    setBody(bodyContent)
                    parameter("key", AppSettingsState.instance.geminiKey)
                }
            val responseString =
                json.decodeFromString<GeminiResponse>(response.bodyAsText())
            val responseBuilder = StringBuilder()
            responseString.candidates.forEach {
                it.content?.parts?.forEach { part ->
                    responseBuilder.append(part.text)
                }
            }
            responseBuilder.toString()
        } catch (e: Exception) {
            e.message ?: ""
        }
    }
}