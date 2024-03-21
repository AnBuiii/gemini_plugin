package com.github.anbuiii.intellijplugin.setting

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


@Suppress("DialogTitleCapitalization")
class AppSettingsComponent {
    var panel: JPanel
        private set

    private val myEnableStatus = JBCheckBox("Enable Gemini?")
    private val myGeminiApiText = JBTextField()

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(myEnableStatus, 1)
            .addVerticalGap(10)
            .addLabeledComponent(JBLabel("API Key"), myGeminiApiText, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = myGeminiApiText

    var geminiApiText: String?
        get() = myGeminiApiText.text
        set(newText) {
            myGeminiApiText.text = newText
        }

    var enableStatus: Boolean
        get() = myEnableStatus.isSelected
        set(newStatus) {
            myEnableStatus.isSelected = newStatus
        }

    var enableApiText: Boolean
        get() = myGeminiApiText.isEnabled
        set(enable) {
            myGeminiApiText.isEnabled = enable
        }
}