package com.github.anbuiii.intellijplugin.network

import com.github.anbuiii.intellijplugin.network.model.Content
import com.github.anbuiii.intellijplugin.network.model.GeminiRequest
import com.github.anbuiii.intellijplugin.network.model.GeminiResponse
import com.github.anbuiii.intellijplugin.network.model.Parts
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.coroutineToIndicator
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import com.intellij.openapi.progress.runBlockingCancellable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}