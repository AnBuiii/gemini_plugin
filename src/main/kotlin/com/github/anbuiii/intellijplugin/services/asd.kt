package com.github.anbuiii.intellijplugin.services

import com.intellij.openapi.components.Service
import kotlinx.coroutines.CoroutineScope

@Service
class ProjectCountingService(
    val cs: CoroutineScope
) {
    private var myOpenProjectCount = 0

    fun increaseOpenProjectCount() {
        myOpenProjectCount++
        println(myOpenProjectCount)
    }

    fun decreaseOpenProjectCount() {
        if (myOpenProjectCount > 0) {
            myOpenProjectCount--
        }
    }

    val isOpenProjectsLimitExceeded: Boolean
        get() = myOpenProjectCount > MAX_OPEN_PROJECTS_LIMIT

    companion object {
        private const val MAX_OPEN_PROJECTS_LIMIT = 3
    }
}