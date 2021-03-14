package com.iron.espresso.model.response.kakao


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_name")
    val addressName: String?,
    @SerializedName("b_code")
    val bCode: String?,
    @SerializedName("h_code")
    val hCode: String?,
    @SerializedName("main_address_no")
    val mainAddressNo: String?,
    @SerializedName("mountain_yn")
    val mountainYn: String?,
    @SerializedName("region_1depth_name")
    val region1depthName: String?,
    @SerializedName("region_2depth_name")
    val region2depthName: String?,
    @SerializedName("region_3depth_h_name")
    val region3depthHName: String?,
    @SerializedName("region_3depth_name")
    val region3depthName: String?,
    @SerializedName("sub_address_no")
    val subAddressNo: String?,
    @SerializedName("x")
    val x: String?,
    @SerializedName("y")
    val y: String?
)