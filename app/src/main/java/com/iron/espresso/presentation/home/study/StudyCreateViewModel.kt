package com.iron.espresso.presentation.home.study

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iron.espresso.AuthHolder
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseViewModel
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.ext.Event
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.ext.plusAssign
import com.iron.espresso.ext.toErrorResponse
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.model.api.StudyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class StudyCreateViewModel @Inject constructor(private val studyApi: StudyApi) :
    BaseViewModel() {

    private val _localItem = MutableLiveData<LocalItem>()
    val localItem: LiveData<LocalItem>
        get() = _localItem

    private val _emptyCheckMessage = MutableLiveData<Event<ValidationInputText>>()
    val emptyCheckMessage: LiveData<Event<ValidationInputText>>
        get() = _emptyCheckMessage

    val image = MutableLiveData<Uri>()

    fun setStudyImage(image: Uri) {
        this.image.value = image
    }

    fun addItems(localItem: LocalItem?) {
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
        createStudyItem: CreateStudyItem
    ): ValidationInputText {
        return when {
            createStudyItem.title.isEmpty() -> ValidationInputText.EMPTY_TITLE
            createStudyItem.introduce.isEmpty() -> ValidationInputText.EMPTY_INTRODUCE
            createStudyItem.progress.isEmpty() -> ValidationInputText.EMPTY_PROGRESS
            (createStudyItem.localItem == null) -> ValidationInputText.EMPTY_PLACE
            createStudyItem.studyTime.isEmpty() -> ValidationInputText.EMPTY_TIME
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

    fun createStudy(createStudyItem: CreateStudyItem) {
        val message = emptyCheck(createStudyItem)
        if (message == ValidationInputText.REGISTER_STUDY && createStudyItem.localItem != null) {
            showLoading()
            compositeDisposable += studyApi
                .registerStudy(
                    bearerToken = AuthHolder.bearerToken,
                    body = RegisterStudyRequest(
                        category = createStudyItem.category,
                        title = createStudyItem.title,
                        introduce = createStudyItem.introduce,
                        progress = createStudyItem.progress,
                        studyTime = createStudyItem.studyTime,
                        latitude = createStudyItem.localItem.lat ?: -1.0,
                        longitude = createStudyItem.localItem.lng ?: -1.0,
                        sido = createStudyItem.localItem.sido,
                        sigungu = createStudyItem.localItem.sigungu,
                        addressName = createStudyItem.localItem.addressName,
                        placeName = createStudyItem.localItem.placeName,
                        locationDetail = createStudyItem.localItem.locationDetail,
                        snsNotion = createStudyItem.snsNotion,
                        snsEverNote = createStudyItem.snsEverNote,
                        snsWeb = createStudyItem.snsWeb,
                        image = createStudyItem.image,
                    ).toMultipartBody()
                )
                .delay(5000L, TimeUnit.MILLISECONDS, Schedulers.io())
                .networkSchedulers()
                .subscribe({
                    _emptyCheckMessage.value = Event(message)
                    hideLoading()
                }, {
                    val errorResponse = (it as? HttpException)?.toErrorResponse()
                    if (errorResponse?.message != null) {
                        if (errorResponse.label != null) {
                            _emptyCheckMessage.value = Event(validationCheck(errorResponse.label.orEmpty()))
                        } else {
                            _toastMessage.value = Event("${errorResponse.message}")
                        }
                    } else {
                        _toastMessage.value = Event("Communication Error")
                    }
                    hideLoading()
                })
        } else {
            _emptyCheckMessage.value = Event(message)
        }
    }
}

enum class ErrorLabel(val label: String) {
    TITLE("title"),
    SNS_NOTION("sns_notion"),
    SNS_EVERNOTE("sns_evernote")
}