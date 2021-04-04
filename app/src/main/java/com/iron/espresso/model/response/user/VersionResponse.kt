package com.iron.espresso.model.response.user


import com.google.gson.annotations.SerializedName
import com.iron.espresso.domain.entity.Version

data class VersionResponse(
    @SerializedName("latest_version")
    val latestVersion: String? = null,
    @SerializedName("force")
    val force: Int = -1,
    @SerializedName("maintenance")
    val maintenance: Boolean? = null
) {
    fun toVersion(): Version =
        Version(latestVersion.orEmpty(), force, maintenance)
}