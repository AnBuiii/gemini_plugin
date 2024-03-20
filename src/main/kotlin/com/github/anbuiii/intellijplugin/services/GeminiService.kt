package com.github.anbuiii.intellijplugin.services

//import com.github.anbuiii.intellijplugin.network.ApiController
import com.github.anbuiii.intellijplugin.network.ApiController
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@Service
class GeminiService(
    private val cs: CoroutineScope
) {

    val controller = ApiController()

    fun getAswer(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val projects = e.getRequiredData(CommonDataKeys.PROJECT)

        val document = editor.document

        // Work off of the primary caret to get the selection info
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        val text = primaryCaret.selectedText ?: ""

        cs.launch {
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
        primaryCaret.removeSelection()
    }
}
