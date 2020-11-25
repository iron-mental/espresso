package com.iron.espresso.model.api

import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.study.MyStudyListResponse
import com.iron.espresso.model.response.study.StudyDetailResponse
import com.iron.espresso.model.response.study.StudyListResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.File

data class RegisterStudyRequest(
    var category: String,
    var title: String,
    var introduce: String,
    var progress: String,
    var studyTime: String,
    val latitude: Double,
    val longitude: Double,
    val sido: String,
    val sigungu: String,
    val addressName: String,
    val placeName: String = "",
    val locationDetail: String = "",
    val snsNotion: String = "",
    val snsEverNote: String = "",
    val snsWeb: String = "",
    val image: File? = null,
) {
    fun toMultipartBody(): List<MultipartBody.Part> {
        return MultipartBody.Builder().run {
            if (category.isNotEmpty()) addFormDataPart("category", category)
            if (title.isNotEmpty()) addFormDataPart("title", title)
            if (introduce.isNotEmpty()) addFormDataPart("introduce", introduce)
            if (progress.isNotEmpty()) addFormDataPart("progress", progress)
            if (studyTime.isNotEmpty()) addFormDataPart("study_time", studyTime)
            if (latitude != null) addFormDataPart("latitude", latitude.toString())
            if (longitude != null) addFormDataPart("longitude", longitude.toString())
            if (sido.isNotEmpty()) addFormDataPart("sido", sido)
            if (sigungu.isNotEmpty()) addFormDataPart("sigungu", sigungu)
            if (addressName.isNotEmpty()) addFormDataPart("address_name", addressName)
            if (placeName.isNotEmpty()) addFormDataPart("place_name", placeName)
            if (locationDetail.isNotEmpty()) addFormDataPart("location_detail", locationDetail)
            if (snsNotion.isNotEmpty()) addFormDataPart("sns_notion", snsNotion)
            if (snsEverNote.isNotEmpty()) addFormDataPart("sns_everNote", snsEverNote)
            if (snsWeb.isNotEmpty()) addFormDataPart("sns_web", snsWeb)
            if (image != null) {
                addFormDataPart(
                    "image",
                    image.name,
                    RequestBody.create(MultipartBody.FORM, image)
                )
            }
            build().parts
        }
    }
}


data class ModifyStudyRequest(
    val category: String = "",
    val title: String = "",
    val introduce: String = "",
    val progress: String = "",
    val studyTime: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val sido: String = "",
    val sigungu: String = "",
    val addressName: String = "",
    val placeName: String = "",
    val locationDetail: String = "",
    val snsNotion: String = "",
    val snsEverNote: String = "",
    val snsWeb: String = "",
    val image: File? = null,
) {
    fun toMultipartBody(): MultipartBody {
        return MultipartBody.Builder().run {
            if (category.isNotEmpty()) addFormDataPart("category", category)
            if (title.isNotEmpty()) addFormDataPart("title", title)
            if (introduce.isNotEmpty()) addFormDataPart("introduce", introduce)
            if (progress.isNotEmpty()) addFormDataPart("progress", progress)
            if (studyTime.isNotEmpty()) addFormDataPart("studyTime", studyTime)
            if (latitude != null) addFormDataPart("latitude", latitude.toString())
            if (longitude != null) addFormDataPart("longitude", longitude.toString())
            if (sido.isNotEmpty()) addFormDataPart("sido", sido)
            if (sigungu.isNotEmpty()) addFormDataPart("sigungu", sigungu)
            if (addressName.isNotEmpty()) addFormDataPart("addressName", addressName)
            if (placeName.isNotEmpty()) addFormDataPart("placeName", placeName)
            if (locationDetail.isNotEmpty()) addFormDataPart("locationDetail", locationDetail)
            if (snsNotion.isNotEmpty()) addFormDataPart("snsNotion", snsNotion)
            if (snsEverNote.isNotEmpty()) addFormDataPart("snsEverNote", snsEverNote)
            if (snsWeb.isNotEmpty()) addFormDataPart("snsWeb", snsWeb)
            if (image != null) {
                addFormDataPart(
                    "image",
                    image.name,
                    RequestBody.create(MultipartBody.FORM, image)
                )
            }

            build()
        }
    }
}

interface StudyApi {


    /** RegisterStudyRequest 만들어서 toMultipartBody */
    @Multipart
    @POST("/v1/study")
    fun registerStudy(
        @Header("Authorization") bearerToken: String,
        @Part body: List<MultipartBody.Part>
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/study/{study_id}")
    fun getStudyDetail(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int
    ): Single<BaseResponse<StudyDetailResponse>>

    /** ModifyStudyRequest 만들어서 toMultipartBody */
    @PUT("/v1/study/{study_id}")
    fun modifyStudy(
        @Header("Authorization") bearerToken: String,
        @Path("study_id") studyId: Int,
        @Part body: MultipartBody
    ): Single<BaseResponse<Nothing>>

    @GET("/v1/user/{id}/study")
    fun getMyStudyList(
        @Header("Authorization") bearerToken: String,
        @Path("id") userId: Int
    ): Single<BaseResponse<MyStudyListResponse>>

    @GET("/v1/study")
    fun getStudyList(
        @Header("Authorization") bearerToken: String,
        @Query("category") category: String,
        @Query("sort") sort: String // new, length
    ): Single<BaseResponse<StudyListResponse>>

    @GET("/v1/study/paging/list")
    fun getStudy(
        @Header("Authorization") bearerToken: String,
        @Query("values") studyIds: String
    ): Single<BaseResponse<StudyListResponse>>
}