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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class ApiController {
    suspend fun askGemini(value: String): String {
        return withContext(Dispatchers.IO) {
            val host = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
            val parameter = "key=AIzaSyDWMjcBrh47GUFWmyptX85WsAw9vkUvzjQ"
            val body = generateRequestBody(value)

            val url = URL("$host?$parameter")
            val httpCon = url.openConnection() as HttpURLConnection
            httpCon.doOutput = true
            httpCon.requestMethod = "POST"
            httpCon.setRequestProperty("Content-Type", "application/json")
            val os: OutputStream = httpCon.outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            osw.write(Json.encodeToString(body))
            osw.flush()
            osw.close()
            os.close() //don't forget to close the OutputStream
            httpCon.connect()

            val bis = BufferedInputStream(httpCon.inputStream)
            val buf = ByteArrayOutputStream()
            var result2 = bis.read()
            while (result2 != -1) {
                buf.write(result2)
                result2 = bis.read()
            }
            val response = Json.decodeFromString<GeminiResponse>(buf.toString())
            val responseBuilder = StringBuilder()
            response.candidates.forEach {
                it.content?.parts?.forEach { part ->
                    responseBuilder.append(part.text)
                }
            }
            responseBuilder.toString()


        }
    }

    private fun generateRequestBody(value: String): GeminiRequest {
        return GeminiRequest(arrayListOf(Content(arrayListOf(Parts(value)))))
    }

    val client by lazy {
        HttpClient(CIO) {
            install(DefaultRequest) {

            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
//            install(ContentNegotiation){
//                json()
//            }
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                    prettyPrint = true
//                    isLenient = true
//                })
//            }
        }
    }

    suspend fun askGemini2(content: String): String {
        return try {
            val body = generateRequestBody(content)

            val bodyContent = Json.encodeToString(body)
            val response =
                client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent") {
                    setBody(bodyContent)
                    parameter("key", AppSettingsState.instance.geminiKey)
                }
            val responseString = Json.decodeFromString<GeminiResponse>(response.bodyAsText())
            val responseBuilder = StringBuilder()
            responseString.candidates.forEach {
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