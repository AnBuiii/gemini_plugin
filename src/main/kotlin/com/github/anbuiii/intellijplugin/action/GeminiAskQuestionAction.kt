package com.github.anbuiii.intellijplugin.action

import com.github.anbuiii.intellijplugin.services.GeminiService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager


class GeminiAskQuestionAction : AnAction() {

    private val service: GeminiService = ApplicationManager.getApplication().getService(
        GeminiService::class.java
    )

    override fun actionPerformed(e: AnActionEvent) {
        service.getAswer(e)
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        // Set visibility and enable only in case of existing project and editor and if a selection exists
        e.presentation.isEnabledAndVisible = project != null && editor != null && editor.selectionModel.hasSelection()
    }
}

