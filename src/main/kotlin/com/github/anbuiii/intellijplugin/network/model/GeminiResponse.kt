package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    @SerializedName("candidates")
    val candidates: ArrayList<Candidates> = arrayListOf(),
    @SerializedName("promptFeedback")
    val promptFeedback: PromptFeedback? = PromptFeedback()
)