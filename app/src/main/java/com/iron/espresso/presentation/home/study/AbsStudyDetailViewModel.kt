package com.iron.espresso.presentation.home.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.StudyDetailItem
import com.iron.espresso.ext.Event

enum class StudySns {
    NOTION, EVER_NOTE, WEB
}

abstract class AbsStudyDetailViewModel :
    BaseViewModel() {

    protected val _studyDetail = MutableLiveData<StudyDetailItem>()
    val studyDetail: LiveData<StudyDetailItem>
        get() = _studyDetail

    private val _showLinkEvent = MutableLiveData<Event<String>>()
    val showLinkEvent: LiveData<Event<String>> get() = _showLinkEvent

    fun clickSns(sns: StudySns) {
        val snsLink = when (sns) {
            StudySns.NOTION -> studyDetail.value?.studyInfoItem?.snsNotion
            StudySns.EVER_NOTE -> studyDetail.value?.studyInfoItem?.snsEvernote
            StudySns.WEB -> studyDetail.value?.studyInfoItem?.snsWeb
        }

        if (!snsLink.isNullOrEmpty()) {
            _showLinkEvent.value = Event(snsLink)
        }
    }

}