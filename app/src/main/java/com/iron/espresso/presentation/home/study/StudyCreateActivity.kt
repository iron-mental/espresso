package com.iron.espresso.presentation.home.study

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.core.net.toFile
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.data.model.CreateStudyItem
import com.iron.espresso.databinding.ActivityCreateStudyBinding
import com.iron.espresso.ext.*
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity.Companion.LOCAL_ITEM
import com.jakewharton.rxbinding4.view.clicks
import com.wswon.picker.ImagePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StudyCreateActivity :
    BaseActivity<ActivityCreateStudyBinding>(R.layout.activity_create_study) {

    private val viewModel by viewModels<StudyCreateViewModel>()

    private var localItem: LocalItem? = null
    private var studyImage: Uri? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.run {
            category.text = intent.getStringExtra(STUDY_CATEGORY).orEmpty()
            introduceInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }
            proceedInputView.setOnTouchListener { v, event -> inputViewTouchEvent(v, event) }

            placeContainer.setOnClickListener {
                startActivityForResult(
                    SearchPlaceActivity.getInstance(this@StudyCreateActivity),
                    REQ_CODE
                )
            }
            image.setOnClickListener {
                checkReadStoragePermission(this@StudyCreateActivity) { isSuccess ->
                    if (isSuccess) {
                        showImagePicker()
                    }
                }
            }

            buttonSignUp.clicks()
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    localItem?.locationDetail = placeDetailInputView.text.toString()

                    viewModel.createStudy(
                        CreateStudyItem(
                            category = category.text.toString(),
                            title = titleInputView.text.toString(),
                            introduce = introduceInputView.text.toString(),
                            progress = proceedInputView.text.toString(),
                            studyTime = timeInputView.text.toString(),
                            localItem = localItem,
                            snsNotion = notionInputView.inputUrl.text.toString(),
                            snsEverNote = evernoteInputView.inputUrl.text.toString(),
                            snsWeb = webInputView.inputUrl.text.toString(),
                            image = studyImage?.toFile()
                        )
                    )
                }, {

                })
        }

        viewModel.run {
            image.observe(this@StudyCreateActivity) { imageUri ->
                binding.image.setImage(imageUri)
                studyImage = imageUri
            }

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
            loadingState.observe(this@StudyCreateActivity, EventObserver(::setLoading))
        }
    }

    private fun showImagePicker() {
        val imagePickerFragment = ImagePickerFragment()
        supportFragmentManager.setFragmentResultListener(
            ImagePickerFragment.REQ_IMAGE,
            this@StudyCreateActivity
        ) { _, data ->
            val imageUri = data.getParcelable<Uri>(ImagePickerFragment.ARG_IMAGE_URI)
            if (imageUri != null) {
                viewModel.setStudyImage(imageUri)
            }
        }

        imagePickerFragment.show(
            supportFragmentManager,
            imagePickerFragment::class.java.simpleName
        )
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
        private const val STUDY_CATEGORY = "study_category"
        private const val REQ_CODE = 0

        fun getIntent(context: Context, category: String) =
            Intent(context, StudyCreateActivity::class.java).apply {
                putExtra(STUDY_CATEGORY, category)
            }
    }
}
