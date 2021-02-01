package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.databinding.ActivityCreateStudyBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.load
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity.Companion.LOCAL_ITEM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyCreateActivity :
    BaseActivity<ActivityCreateStudyBinding>(R.layout.activity_create_study) {

    private val viewModel by viewModels<StudyCreateViewModel>()

    private var localItem: LocalItem? = null

    private val categoryImage by lazy {
        intent.getIntExtra(KEY, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.run {
            image.transitionName = categoryImage.toString()
            image.load(categoryImage, true) {
                supportPostponeEnterTransition()
            }

            introduceInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            proceedInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }

            placeContainer.setOnClickListener {
                startActivityForResult(
                    SearchPlaceActivity.getInstance(this@StudyCreateActivity),
                    REQ_CODE
                )
            }

            buttonSignUp.setOnClickListener {
                localItem?.locationDetail = placeDetailInputView.text.toString()

                viewModel.createStudy(
                    CreateStudyItem(
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
            localItem.observe(this@StudyCreateActivity) { localItem ->
                val placeDetailText = localItem.addressName + " " + localItem.placeName
                binding.placeDetail.text = placeDetailText
                binding.placeDetailInputView.setText(localItem.locationDetail)
            }

            emptyCheckMessage.observe(this@StudyCreateActivity, EventObserver { message ->
                toast(message.resId)
                if (message == ValidationInputText.REGISTER_STUDY) {
                    finish()
                }
            })

            toastMessage.observe(this@StudyCreateActivity, EventObserver { message ->
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
            localItem = data?.getSerializableExtra(LOCAL_ITEM) as LocalItem
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

        private const val TITLE = "스터디 만들기"
        private const val KEY = "key"
        private const val REQ_CODE = 0

        fun getIntent(context: Context, item: Int) =
            Intent(context, StudyCreateActivity::class.java).apply {
                putExtra(KEY, item)
            }

    }
}
