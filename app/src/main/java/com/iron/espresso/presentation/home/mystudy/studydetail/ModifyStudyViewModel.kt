package com.iron.espresso.presentation.home.mystudy.studydetail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.Logger
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.LocationItem
import com.iron.espresso.data.model.ModifyStudyItem
import com.iron.espresso.domain.repo.StudyRepository
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.ModifyStudyRequest
import com.iron.espresso.presentation.home.study.ErrorLabel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ModifyStudyViewModel @Inject constructor(private val studyRepository: StudyRepository) :
    BaseViewModel() {

    private val _localItem = MutableLiveData<LocalItem>()
    val localItem: LiveData<LocalItem>
        get() = _localItem

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    private val _success = MutableLiveData<Event<Unit>>()
    val success: LiveData<Event<Unit>>
        get() = _success

    val image = MutableLiveData<Uri>()

    val notion = MutableLiveData<String>()
    val everNote = MutableLiveData<String>()
    val web = MutableLiveData<String>()

    fun initSnsData(notion: String, everNote: String, web: String) {
        this.notion.value = notion
        this.everNote.value = everNote
        this.web.value = web
    }

    fun initLocalItem(locationItem: LocationItem) {
        _localItem.value = LocalItem(
            lat = locationItem.latitude.toDouble(),
            lng = locationItem.longitude.toDouble(),
            addressName = locationItem.addressName,
            placeName = locationItem.placeName ?: "",
            locationDetail = locationItem.locationDetail ?: ""
        )
    }

    fun setStudyImage(image: Uri) {
        this.image.value = image
    }

    fun replaceLocalItem(localItem: LocalItem?) {
        if (localItem != null) {
            _localItem.value = LocalItem(
                lat = localItem.lat,
                lng = localItem.lng,
                sido = localItem.sido,
                sigungu = localItem.sigungu,
                addressName = localItem.addressName,
                placeName = localItem.placeName,
                locationDetail = localItem.locationDetail
            )
        }
    }

    private fun emptyCheck(
        modifyStudyItem: ModifyStudyItem
    ): ValidationInputText {
        return when {
            modifyStudyItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            modifyStudyItem.introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            modifyStudyItem.progress.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            modifyStudyItem.studyTime.isEmpty() -> ValidationInputText.EMPTY_TIME
            else -> ValidationInputText.REGISTER_STUDY
        }
    }

    private fun validationCheck(label: String): ValidationInputText {
        return when (label) {
            ErrorLabel.TITLE.label -> ValidationInputText.LENGTH_TITLE
            ErrorLabel.SNS_NOTION.label -> ValidationInputText.SNS_NOTION
            ErrorLabel.SNS_EVERNOTE.label -> ValidationInputText.SNS_EVERNOTE
            else -> error("is not label")
        }
    }

    private fun duplicateItemCheck(title: String, modifyTitle: String): String {
        return if (title == modifyTitle) "" else modifyTitle
    }

    fun modifyStudy(studyId: Int, title: String, modifyStudyItem: ModifyStudyItem) {
        val message = emptyCheck(modifyStudyItem)
        if (message == ValidationInputText.REGISTER_STUDY) {
            compositeDisposable += studyRepository
                .modifyStudy(
                    studyId = studyId,
                    request = ModifyStudyRequest(
                        category = modifyStudyItem.category,
                        title = duplicateItemCheck(title, modifyStudyItem.title),
                        introduce = modifyStudyItem.introduce,
                        progress = modifyStudyItem.progress,
                        studyTime = modifyStudyItem.studyTime,
                        latitude = localItem.value?.lat,
                        longitude = localItem.value?.lng,
                        sido = localItem.value?.sido.orEmpty(),
                        sigungu = localItem.value?.sigungu.orEmpty(),
                        addressName = localItem.value?.addressName.orEmpty(),
                        placeName = localItem.value?.placeName,
                        locationDetail = modifyStudyItem.locationDetail,
                        snsNotion = modifyStudyItem.snsNotion,
                        snsEverNote = modifyStudyItem.snsEverNote,
                        snsWeb = modifyStudyItem.snsWeb,
                        image = modifyStudyItem.image,
                    )
                )
                .networkSchedulers()
                .subscribe({
                    if (it.result) {
                        _success.value = Event(Unit)
                        _emptyCheckMessage.value = Event(message)
                    }
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                    val errorResponse = it.toErrorResponse()
                    if (errorResponse?.message != null) {
                        if (errorResponse.label != null) {
                            _emptyCheckMessage.value = Event(validationCheck(errorResponse.label.orEmpty()))
                        } else {
                            _toastMessage.value = Event("${errorResponse.message}")
                        }
                    } else {
                        _toastMessage.value = Event("Communication Error")
                    }
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}