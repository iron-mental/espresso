package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName

data class LocalResponse(
    @SerializedName("meta")
    val meta: LocalMeta,
    @SerializedName("documents")
    val documents: List<TotalAddress>
)

data class LocalMeta(
    @SerializedName("total_count")
    val totalCount: Int
)

data class TotalAddress(
    @SerializedName("address")
    val address: Address,
    @SerializedName("road_address")
    val roadAddress: RoadAddress
)

data class Address(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("region_1depth_name")
    val region1depthName: String,
    @SerializedName("region_2depth_name")
    val region2depthName: String,
    @SerializedName("region_3depth_name")
    val region3depthName: String,
    @SerializedName("mountain_yn")
    val mountain_Yn: String,
    @SerializedName("main_address_no")
    val mainAddressNo: String,
    @SerializedName("sub_address_no")
    val subAddressNo: String
)

data class RoadAddress(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("region_1depth_name")
    val region1depthName: String,
    @SerializedName("region_2depth_name")
    val region2depthName: String,
    @SerializedName("region_3depth_name")
    val region3depthName: String,
    @SerializedName("road_name")
    val roadName: String,
    @SerializedName("underground_yn")
    val undergroundYn: String,
    @SerializedName("main_building_no")
    val mainBuildingNo: String,
    @SerializedName("sub_building_no")
    val subBuildingNo: String,
    @SerializedName("building_name")
    val buildingName: String,
    @SerializedName("zone_no")
    val zoneNo: String
)