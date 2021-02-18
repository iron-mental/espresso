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
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.ModifyStudyItem
import com.iron.espresso.data.model.StudyInfoItem
import com.iron.espresso.databinding.ActivityModifyStudyBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.mystudy.ModifyStudyViewModel
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyStudyActivity :
    BaseActivity<ActivityModifyStudyBinding>(R.layout.activity_modify_study) {

    private val viewModel by viewModels<ModifyStudyViewModel>()

    private var localItem: LocalItem? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)
        val studyInfoItem = intent.getSerializableExtra(STUDY_INFO) as StudyInfoItem

        binding.run {
            titleInputView.setText(studyInfoItem.title)
            introduceInputView.setText(studyInfoItem.introduce)
            introduceInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            proceedInputView.setText(studyInfoItem.progress)
            proceedInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            placeDetail.text =
                studyInfoItem.locationItem.addressName + " " + studyInfoItem.locationItem.placeName
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
                localItem?.locationDetail = placeDetailInputView.text.toString()

                viewModel.modifyStudy(
                    476,
                    ModifyStudyItem(
                        category = "android",
                        title = titleInputView.text.toString(),
                        introduce = introduceInputView.text.toString(),
                        progress = proceedInputView.text.toString(),
                        studyTime = timeInputView.text.toString(),
                        localItem = localItem,
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
                if (message == ValidationInputText.REGISTER_STUDY) {
                    finish()
                }
            })

            toastMessage.observe(this@ModifyStudyActivity, EventObserver { message ->
                toast(message)
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