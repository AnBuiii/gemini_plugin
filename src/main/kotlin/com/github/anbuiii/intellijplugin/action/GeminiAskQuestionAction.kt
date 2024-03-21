package com.github.anbuiii.intellijplugin.action

import com.github.anbuiii.intellijplugin.services.GeminiService
import com.github.anbuiii.intellijplugin.setting.AppSettingsState
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager


class GeminiAskQuestionAction : AnAction() {

    private val service: GeminiService = ApplicationManager.getApplication().getService(
        GeminiService::class.java
    )

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(e: AnActionEvent) {
        service.getAnswer(e)
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        e.presentation.isEnabledAndVisible =
            project != null && editor != null && editor.selectionModel.hasSelection() && AppSettingsState.instance.enable
    }
}

