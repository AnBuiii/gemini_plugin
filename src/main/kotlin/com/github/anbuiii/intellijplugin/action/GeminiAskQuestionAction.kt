package com.github.anbuiii.intellijplugin.action

import com.github.anbuiii.intellijplugin.services.GeminiService
import com.github.anbuiii.intellijplugin.setting.AppSettingsState
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.platform.ide.progress.withBackgroundProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("UnstableApiUsage")
class GeminiAskQuestionAction : AnAction() {

    private val service: GeminiService = ApplicationManager.getApplication().getService(
        GeminiService::class.java
    )

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val projects = e.getRequiredData(CommonDataKeys.PROJECT)
        val project = e.project!!

        val document = editor.document

        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart

        val text = primaryCaret.selectedText ?: ""
        service.cs.launch {
            ensureActive()
            val answer = withContext(Dispatchers.Main) {
                withBackgroundProgress(project, "Gemini is generating answer...", true) {
                    service.getAnswer(text)
                }
            }
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

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        e.presentation.isEnabledAndVisible =
            project != null && editor != null && editor.selectionModel.hasSelection() && AppSettingsState.instance.enable
    }
}

