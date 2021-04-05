package com.iron.espresso.presentation.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.model.api.KakaoApi
import com.iron.espresso.model.response.Place
import com.iron.espresso.model.response.PlaceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(private val kakaoApi: KakaoApi) :
    BaseViewModel() {

    val placeList = MutableLiveData<List<Place>>()

    fun searchPlace(keyword: String) {
        kakaoApi.getPlacesByKeyword(SearchPlaceActivity.REST_API_KEY, keyword)
            .enqueue(object : Callback<PlaceResponse> {
                override fun onResponse(
                    call: Call<PlaceResponse>,
                    response: Response<PlaceResponse>
                ) {
                    Log.d("test", response.body().toString())
                    placeList.value = response.body()?.documents
                }

                override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                    Log.d("TAG", "실패 : $t")
                }
            })
    }

}