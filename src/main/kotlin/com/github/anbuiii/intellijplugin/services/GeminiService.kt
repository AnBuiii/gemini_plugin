package com.github.anbuiii.intellijplugin.services

import com.github.anbuiii.intellijplugin.network.ApiController
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.progress.*
import com.intellij.platform.ide.progress.withBackgroundProgress
import io.ktor.client.*
import kotlinx.coroutines.*

@Suppress("UnstableApiUsage")
@Service
class GeminiService(
    private val cs: CoroutineScope
) {

    private val controller = ApiController()

    fun getAnswer(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val projects = e.getRequiredData(CommonDataKeys.PROJECT)
        val project = e.project!!

        val document = editor.document

        // Work off of the primary caret to get the selection info
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        val text = primaryCaret.selectedText ?: ""

//        ProgressManager.getInstance().run(Task.)
        cs.launch {
            ensureActive()
            withContext(Dispatchers.Main) {
                withBackgroundProgress(project, "Gemini is generating answer...", true) {
                    var answer = controller.askGemini(text)
                    answer = "/**\n" +
                            "$answer\n" +
                            "*/\n"

                    WriteCommandAction.runWriteCommandAction(
                        projects
                    ) {
                        document.replaceString(
                            start,
                            start,
                            answer,
                        )
                    }
                }
            }
//
        }

        primaryCaret.removeSelection()
    }
}
