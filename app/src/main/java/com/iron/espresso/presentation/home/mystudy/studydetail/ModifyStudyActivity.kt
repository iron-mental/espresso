package com.iron.espresso.presentation.home.mystudy.studydetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.ModifyStudyItem
import com.iron.espresso.data.model.StudyInfoItem
import com.iron.espresso.databinding.ActivityModifyStudyBinding
import com.iron.espresso.ext.*
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyStudyActivity :
    BaseActivity<ActivityModifyStudyBinding>(R.layout.activity_modify_study) {

    private val viewModel by viewModels<ModifyStudyViewModel>()

    private var localItem = LocalItem()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)
        val studyInfoItem = intent.getSerializableExtra(STUDY_INFO) as StudyInfoItem

        binding.run {
            image.setUrlImg(studyInfoItem.image, ImageType.NORMAl)
            titleInputView.setText(studyInfoItem.title)
            introduceInputView.apply {
                setText(studyInfoItem.introduce)
                setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            }
            proceedInputView.apply {
                setText(studyInfoItem.progress)
                setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            }
            placeDetail.text = studyInfoItem.locationItem.run {
                "$addressName $placeName"
            }
            placeDetailInputView.setText(studyInfoItem.locationItem.locationDetail)
            timeInputView.setText(studyInfoItem.studyTime)
            notionInputView.inputUrl.setText(studyInfoItem.snsNotion)
            evernoteInputView.inputUrl.setText(studyInfoItem.snsEvernote)
            webInputView.inputUrl.setText(studyInfoItem.snsWeb)

            placeContainer.setOnClickListener {
                startActivityForResult(
                    SearchPlaceActivity.getInstance(this@ModifyStudyActivity),
                    REQ_CODE
                )
            }

            buttonSignUp.setOnClickListener {
                localItem.locationDetail = placeDetailInputView.text.toString()

                viewModel.modifyStudy(
                    studyInfoItem.id,
                    studyInfoItem.title,
                    ModifyStudyItem(
                        category = studyInfoItem.category,
                        title = titleInputView.text.toString(),
                        introduce = introduceInputView.text.toString(),
                        progress = proceedInputView.text.toString(),
                        studyTime = timeInputView.text.toString(),
                        latitude = localItem.lat,
                        longitude = localItem.lng,
                        sido = localItem.sido,
                        sigungu = localItem.sigungu,
                        addressName = localItem.addressName,
                        placeName = localItem.placeName,
                        locationDetail = localItem.locationDetail,
                        snsNotion = notionInputView.inputUrl.text.toString(),
                        snsEverNote = evernoteInputView.inputUrl.text.toString(),
                        snsWeb = webInputView.inputUrl.text.toString(),
                        image = null
                    )
                )
            }
        }

        viewModel.run {
            localItem.observe(this@ModifyStudyActivity) { localItem ->
                val placeDetailText = localItem.addressName + " " + localItem.placeName
                binding.placeDetail.text = placeDetailText
                binding.placeDetailInputView.setText(localItem.locationDetail)
            }

            emptyCheckMessage.observe(this@ModifyStudyActivity, EventObserver { message ->
                toast(message.resId)
            })

            toastMessage.observe(this@ModifyStudyActivity, EventObserver { message ->
                toast(message)
            })

            success.observe(this@ModifyStudyActivity, EventObserver {
                setResult(RESULT_OK)
                finish()
            })
        }
    }

    private fun inputViewTouchEvent(v: View, event: MotionEvent): Boolean {
        if (v.hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return true
                }
            }
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            localItem =
                data?.getSerializableExtra(SearchPlaceDetailActivity.LOCAL_ITEM) as LocalItem
            viewModel.addItems(localItem)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {

        private const val TITLE = "스터디 수정하기"
        private const val REQ_CODE = 0
        private const val STUDY_INFO = "studyInfoItem"

        fun getIntent(context: Context, studyInfoItem: StudyInfoItem) =
            Intent(context, ModifyStudyActivity::class.java)
                .putExtra(STUDY_INFO, studyInfoItem)
    }
}