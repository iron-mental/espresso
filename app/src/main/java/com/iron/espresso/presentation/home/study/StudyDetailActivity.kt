package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.ext.*
import com.iron.espresso.presentation.home.apply.ApplyStudyDialog
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyDetailActivity :
    BaseActivity<ActivityStudyDetailBinding>(R.layout.activity_study_detail) {

    private val viewModel by viewModels<StudyDetailViewModel>()

    private val studyId: Int
        get() = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(R.string.title_study_detail)
        setNavigationIcon(R.drawable.ic_back_24)

        setupView()
        setupViewModel()

        viewModel.getStudy(studyId)

        binding.joinButton.setOnClickListener {
            showApplyDialog()
        }

        viewModel.emptyCheckMessage.observe(this, EventObserver {
            toast(it.resId)
        })

        viewModel.studyDetail.observe(this, Observer { studyDetail ->
            visibleBtn(studyDetail.authority)
            binding.run {
                introduceDetail.text = studyDetail.introduce
                proceedDetail.text = studyDetail.progress
                timeDetail.text = studyDetail.studyTime
                placeDetail.text = studyDetail.locationItem.run {
                    "$addressName $placeName"
                }
                numberMember.text = studyDetail.participateItem.size.toString()

                if (studyDetail.image.isNotEmpty()) {
                    image.setRadiusImage(studyDetail.image)
                }
            }

            /* 구성원 수 만큼 동적 생성 */
            studyDetail.participateItem.forEach { memberList ->
                val memberView = LayoutInflater.from(this)
                    .inflate(R.layout.item_member, binding.memberContainer, false)

                val memberNickname: TextView = memberView.findViewById(R.id.member_nickname)
                val memberImage: ImageView = memberView.findViewById(R.id.member_image)

                memberNickname.text = memberList.nickname

                if (!memberList.image.isNullOrEmpty()) {
                    memberImage.setCircleImage(memberList.image)
                }

                binding.memberContainer.addView(memberView)
            }

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
                }

            mapFragment.getMapAsync { naverMap ->
//                naverMap.cameraPosition = CameraPosition(
//                    LatLng(
//                        studyDetail.locationItem.longitude.toDouble(),
//                        studyDetail.locationItem.latitude.toDouble()
//                    ), 16.0
//                )
            }
        })
    }

    private fun setupView() {

    }

    private fun setupViewModel() {
        with(viewModel) {
            toastMessage.observe(this@StudyDetailActivity, EventObserver(::toast))
            loadingState.observe(this@StudyDetailActivity, EventObserver(::setLoading))
            success.observe(this@StudyDetailActivity, EventObserver {
                visibleBtn(AUTHORITY_APPLIER)
            })
        }
    }

    private fun visibleBtn(authority: String) {
        when (authority) {
            AUTHORITY_APPLIER -> {
                binding.joinButton.visibility = View.INVISIBLE
                binding.joiningButton.visibility = View.VISIBLE
            }
            AUTHORITY_REJECT -> {

            }
            else -> {
                binding.joinButton.visibility = View.VISIBLE
                binding.joiningButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun showApplyDialog() {
        val dialog = ApplyStudyDialog.newInstance()

        dialog.show(supportFragmentManager, dialog::class.simpleName)

        supportFragmentManager.setFragmentResultListener(
            ApplyStudyDialog.SUBMIT,
            this
        ) { _: String, bundle: Bundle ->
            val message = bundle.getString(ApplyStudyDialog.MESSAGE)

            viewModel.sendApply(studyId, message.orEmpty())
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
        private const val STUDY_ID = "studyId"
        private const val DEFAULT_VALUE = -1
        private const val AUTHORITY_APPLIER = "applier"
        private const val AUTHORITY_REJECT = "reject"


        fun getIntent(context: Context, studyId: Int) =
            Intent(context, StudyDetailActivity::class.java)
                .putExtra(STUDY_ID, studyId)
    }
}