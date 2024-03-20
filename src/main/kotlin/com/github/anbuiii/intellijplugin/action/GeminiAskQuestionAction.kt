package com.github.anbuiii.intellijplugin.action

import com.github.anbuiii.intellijplugin.services.GeminiService
import com.github.anbuiii.intellijplugin.services.MyProjectService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service


class GeminiAskQuestionAction : AnAction() {
    lateinit var service: MyProjectService
    override fun actionPerformed(e: AnActionEvent) {
        println(service.getRandomNumber())

//        val projectService = ApplicationManager.getApplication().getService(GeminiService::class.java);
//        projectService?.getAswer(e) ?: run {
//            println("??")
//        }
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        // Set visibility and enable only in case of existing project and editor and if a selection exists
        e.presentation.isEnabledAndVisible = project != null && editor != null && editor.selectionModel.hasSelection()
        if (project != null) {
            if (!::service.isInitialized) {
                service = project.service<MyProjectService>()
            }
        }

    }
}

