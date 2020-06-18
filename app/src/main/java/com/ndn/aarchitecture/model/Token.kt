package com.ndn.aarchitecture.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(
    @Expose
    @SerializedName("access_token")
    val accessToken: String? = null,
    @Expose
    @SerializedName("expires_at")
    val expiresAt: String? = null,
    @Expose
    @SerializedName("token_type")
    val tokenType: String? = null,
    @Expose
    @SerializedName("use_in_maintenance")
    val useInMaintenance: Boolean? = null
)
