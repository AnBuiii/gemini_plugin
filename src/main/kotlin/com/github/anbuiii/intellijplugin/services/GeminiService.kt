package com.github.anbuiii.intellijplugin.services

import com.github.anbuiii.intellijplugin.network.ApiController
import com.intellij.openapi.components.Service
import kotlinx.coroutines.*

@Service
class GeminiService(
    val cs: CoroutineScope
) {
    private val controller = ApiController()
    suspend fun getAnswer(question: String): String {
        val explainQuestion = "Explain me this code:\n$question"
        var answer = controller.askGemini2(explainQuestion)
        answer = "/**\n" +
                "$answer\n" +
                "*/\n"

        return answer
    }
}
