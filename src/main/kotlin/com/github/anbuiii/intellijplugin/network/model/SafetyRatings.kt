package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SafetyRatings(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("probability")
    val probability: String? = null

)