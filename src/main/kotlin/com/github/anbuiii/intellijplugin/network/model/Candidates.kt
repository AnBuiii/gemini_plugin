package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Candidates(
    @SerializedName("content")
    val content: Content? = Content(),
    @SerializedName("finishReason")
    val finishReason: String? = null,
    @SerializedName("index")
    val index: Int? = null,
    @SerializedName("safetyRatings")
    val safetyRatings: ArrayList<SafetyRatings> = arrayListOf()
)