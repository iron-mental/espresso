package com.iron.espresso.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceResponse(
    @SerializedName("meta")
    val meta: PlaceMeta,
    @SerializedName("documents")
    val documents: List<Place>
)

data class PlaceMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("same_name")
    val sameName: RegionInfo
)

data class RegionInfo(
    @SerializedName("region")
    val region: List<String>,
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("selected_region")
    val selected_region: String
)

data class Place(
    @SerializedName("id")
    val id: String,
    @SerializedName("place_name")
    val place_name: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_group_code")
    val categoryGroupCode: String,
    @SerializedName("category_group_name")
    val categoryGroupName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("place_url")
    val place_url: String,
    @SerializedName("distance")
    val distance: String
) : Serializable