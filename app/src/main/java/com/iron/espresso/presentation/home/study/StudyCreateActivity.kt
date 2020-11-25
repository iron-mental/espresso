package com.iron.espresso.presentation.home.study

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.LocalItem
import com.iron.espresso.databinding.ActivityCreateStudyBinding
import com.iron.espresso.ext.load
import com.iron.espresso.model.api.RegisterStudyRequest
import com.iron.espresso.presentation.place.SearchPlaceActivity
import com.iron.espresso.presentation.place.SearchPlaceDetailActivity.Companion.LOCAL_ITEM

class StudyCreateActivity :
    BaseActivity<ActivityCreateStudyBinding>(R.layout.activity_create_study) {

    private val viewModel: StudyCreateViewModel by viewModels()

    private lateinit var registerStudyRequest: RegisterStudyRequest

    private val image by lazy {
        intent.getIntExtra(KEY, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_study)
        binding.lifecycleOwner = this

        setToolbarTitle(TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        binding.image.transitionName = image.toString()
        binding.image.load(image, true) {
            supportPostponeEnterTransition()
        }

        ObjectAnimator.ofFloat(binding.title, View.ALPHA, 0.2f, 1.0f).apply {
            duration = 1500
            start()
        }

        binding.placeContainer.setOnClickListener {
            startActivityForResult(SearchPlaceActivity.getInstance(this), REQ_CODE)
        }

        viewModel.registerStudyRequest.observe(this) { registerStudyRequest ->
            this.registerStudyRequest = registerStudyRequest
            binding.placeDetail.text = registerStudyRequest.addressName
        }

        binding.buttonSignUp.setOnClickListener {
            Log.d("test2", "test2")

            registerStudyRequest.run {
                category = "ios"
                title = binding.title.text.toString()
                introduce = binding.introduce.text.toString()
                progress = binding.proceed.text.toString()
                studyTime = binding.time.text.toString()
            }

            binding.run {
                when {
                    title.text.isEmpty() -> Toast.makeText(
                        this@StudyCreateActivity,
                        "제목을 입력하세요",
                        Toast.LENGTH_SHORT
                    ).show()
                    placeDetail.text == "장소" -> Toast.makeText(
                        this@StudyCreateActivity,
                        "장소를 선택하세요",
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {
                        viewModel.createStudy(registerStudyRequest)
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            val items = data?.getSerializableExtra(LOCAL_ITEM) as LocalItem
            Log.d(LOCAL_ITEM, items.toString())
            viewModel.addItems(items)
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
