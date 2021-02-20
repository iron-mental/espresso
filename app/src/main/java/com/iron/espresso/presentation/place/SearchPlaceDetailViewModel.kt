package com.iron.espresso.presentation.place

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.LocalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPlaceDetailViewModel @ViewModelInject constructor(private val kakaoApi: KakaoApi) :
    BaseViewModel() {

    val sendLocalItem = MutableLiveData<LocalItem>()

    fun searchCoord(lng: Double, lat: Double, placeName: String) {

        kakaoApi.convertAddressToCoord(
            authorization = SearchPlaceActivity.REST_API_KEY,
            lng = lng,
            lat = lat
        )
            .enqueue(object : Callback<LocalResponse> {
                override fun onResponse(
                    call: Call<LocalResponse>,
                    response: Response<LocalResponse>
                ) {
                    val roadAddress = response.body()?.documents?.getOrNull(0)?.roadAddress
                    val address = response.body()?.documents?.getOrNull(0)?.address

                    val addressName = roadAddress?.addressName ?: address?.addressName ?: ""

                    val depth1 = roadAddress?.region1depthName ?: address?.region1depthName ?: ""
                    val depth2 = roadAddress?.region2depthName ?: address?.region2depthName ?: ""

                    sendLocalItem.value = LocalItem(
                        lat,
                        lng,
                        depth1,
                        depth2,
                        addressName,
                        placeName,
                        ""
                    )
                }

                override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                    Log.d("TAG", "실패 : $t")
                }
            })
    }

}