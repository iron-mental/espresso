package com.iron.espresso.presentation.home.study

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.databinding.ActivityCreateStudyBinding
import com.iron.espresso.di.ApiModule
import com.iron.espresso.ext.load
import com.iron.espresso.ext.networkSchedulers
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity.Companion.LOCAL_ITEM
import com.iron.espresso.utils.ToolbarHelper
import java.io.File

class StudyCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateStudyBinding

    private lateinit var toolbarHelper: ToolbarHelper


    private val image by lazy {
        intent.getIntExtra(KEY, 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_study)
        binding.lifecycleOwner = this

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TITLE)
            setNavigationIcon(R.drawable.ic_back_24)
        }

        binding.placeContainer.setOnClickListener {
            startActivityForResult(SearchPlaceActivity.getInstance(this), REQ_CODE)
        }

        binding.image.transitionName = image.toString()
        binding.image.load(image, true) {
            supportPostponeEnterTransition()
        }

        ObjectAnimator.ofFloat(binding.title, View.ALPHA, 0.2f, 1.0f).apply {
            duration = 1500
            start()
        }

        binding.buttonSignUp.setOnClickListener {
            val token = "Access Token"
            ApiModule.provideStudyApi()
                .registerStudy(
                    bearerToken = "Bearer $token",
                    body = RegisterStudyRequest(
                        category = "ios",
                        title = "tset",
                        introduce = "test",
                        progress = "test",
                        studyTime = "tes5wereasafsaft",
                        latitude = 1.0,
                        longitude = 1.0,
                        sido = "test",
                        sigungu = "test",
                        addressName = "test",
                        placeName = "test",
                        locationDetail = "test",
                        snsNotion = "",
                        snsEverNote = "",
                        snsWeb = "",
                        image = File("/storage/0000-0000/DCIM/Camera/20201018_142454.JPG")
                    ).toMultipartBody()
                )
                .networkSchedulers()
                .subscribe({
                    Logger.d("$it")
                }, {
                    Logger.d("$it")
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                val items = data?.getSerializableExtra(LOCAL_ITEM) as LocalItem
                Log.d(LOCAL_ITEM, items.toString())

                binding.placeDetail.text = items.addressName
            }
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

        fun getInstance(context: Context, item: Int) =
            Intent(context, StudyCreateActivity::class.java).apply {
                putExtra(KEY, item)
            }

    }


}