package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    @SerializedName("contents")
    val contents: ArrayList<Content> = arrayListOf()
)