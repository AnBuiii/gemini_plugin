package com.github.anbuiii.intellijplugin.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
    @SerializedName("parts")
    val parts: ArrayList<Parts> = arrayListOf(),
    @SerializedName("role")
    val role: String? = null
)