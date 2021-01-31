package com.iron.espresso.model.response.study


import com.google.gson.annotations.SerializedName
import com.iron.espresso.data.model.LocationItem
import com.iron.espresso.data.model.StudyDetailItem

data class StudyDetailResponse(
    @SerializedName("participate")
    val participateResponse: List<ParticipateResponse>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("introduce")
    val introduce: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("progress")
    val progress: String?,
    @SerializedName("study_time")
    val studyTime: String?,
    @SerializedName("sns_notion")
    val snsNotion: String?,
    @SerializedName("sns_evernote")
    val snsEvernote: String?,
    @SerializedName("sns_web")
    val snsWeb: String?,
    @SerializedName("location")
    val locationResponse: LocationResponse?,
    @SerializedName("Authority")
    val authority: String?
) {
    fun toStudyDetailItem() =
        StudyDetailItem(
            participateItem = participateResponse?.map {
                it.toParticipateItem()
            }.orEmpty(),
            id = id ?: -1,
            category = category.orEmpty(),
            title = title.orEmpty(),
            introduce = introduce.orEmpty(),
            image = image.orEmpty(),
            progress = progress.orEmpty(),
            studyTime = studyTime.orEmpty(),
            snsNotion = snsNotion.orEmpty(),
            snsEvernote = snsEvernote.orEmpty(),
            snsWeb = snsWeb.orEmpty(),
            locationItem = (locationResponse?.toLocationItem()) ?: LocationItem(),
            authority = authority.orEmpty()
        )
}