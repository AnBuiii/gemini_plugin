package com.github.anbuiii.intellijplugin.network

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
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
            val body = GeminiRequest(arrayListOf(Content(arrayListOf(Parts(value)))))

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