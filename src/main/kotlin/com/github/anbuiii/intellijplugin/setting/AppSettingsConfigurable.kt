package com.github.anbuiii.intellijplugin.setting

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent


/**
 * Provides controller functionality for application settings.
 * Crash aware?
 */
internal class AppSettingsConfigurable : Configurable {
    private var mySettingsComponent: AppSettingsComponent? = null
    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings: AppSettingsState = AppSettingsState.instance
        mySettingsComponent!!.enableApiText = mySettingsComponent!!.enableStatus
        var modified = mySettingsComponent!!.geminiApiText != settings.geminiKey
        modified = modified or (mySettingsComponent!!.enableStatus != settings.enable)
        return modified
    }

    override fun apply() {
        val settings: AppSettingsState = AppSettingsState.instance
        settings.geminiKey = mySettingsComponent!!.geminiApiText ?: ""
        settings.enable = mySettingsComponent!!.enableStatus
    }

    override fun getDisplayName(): String {
        return "Gemini"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    override fun reset() {
        val settings: AppSettingsState = AppSettingsState.instance
        mySettingsComponent!!.geminiApiText = settings.geminiKey
        mySettingsComponent!!.enableStatus = settings.enable
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}