package com.iron.espresso.presentation.home.mystudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.MyStudyItem

class MyStudyViewModel @ViewModelInject constructor(): BaseViewModel() {

    private val _movieList =
        MutableLiveData<List<MyStudyItem>>()
    val movieList: LiveData<List<MyStudyItem>>
        get() = _movieList


    fun showMyStudyList() {
        val myStudyItem = mutableListOf<MyStudyItem>()
        myStudyItem.add(MyStudyItem("안드로이드 스터디", "강남역 윙스터디", ""))
        myStudyItem.add(MyStudyItem("Swift 정복하기", "사당역 스타벅스", ""))
        myStudyItem.add(MyStudyItem("JavaScript 뿌시기", "사당역 카페베네", ""))
        _movieList.value = myStudyItem
    }

}