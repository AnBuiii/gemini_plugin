package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Parts(
    @SerializedName("text")
    val text: String? = null
)