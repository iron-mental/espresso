package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.ext.*
import com.iron.espresso.presentation.home.apply.ApplyStudyDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
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

        setToolbarTitle(intent.getStringExtra(KEY_TOOLBAR_TITLE).orEmpty())
        setNavigationIcon(R.drawable.ic_back_24)

        setupView()
        setupViewModel()

        viewModel.getStudy(studyId)

        binding.viewModel = viewModel

        binding.joinButton.setOnClickListener {
            showApplyDialog()
        }

        viewModel.emptyCheckMessage.observe(this, EventObserver {
            toast(it.resId)
        })

        viewModel.studyDetail.observe(this, { studyDetail ->
            setToolbarTitle(studyDetail.studyInfoItem.title)
            visibleBtn(studyDetail.studyInfoItem.authority)
            binding.run {
                introduceDetail.text = studyDetail.studyInfoItem.introduce
                proceedDetail.text = studyDetail.studyInfoItem.progress
                timeDetail.text = studyDetail.studyInfoItem.studyTime
                placeDetail.text = studyDetail.studyInfoItem.locationItem.run {
                    "$addressName $placeName"
                }
                numberMember.text = studyDetail.studyInfoItem.participateItem.size.toString()

                if (!studyDetail.studyInfoItem.image.isNullOrEmpty()) {
                    image.setRadiusImage(studyDetail.studyInfoItem.image)
                }
                changeImage.isVisible = studyDetail.studyInfoItem.image.isNullOrEmpty()
            }

            /* 구성원 수 만큼 동적 생성 */
            studyDetail.studyInfoItem.participateItem.forEach { memberList ->
                val memberView = LayoutInflater.from(this)
                    .inflate(R.layout.item_member, binding.memberContainer, false)

                val memberNickname: TextView = memberView.findViewById(R.id.member_nickname)
                val memberImage: ImageView = memberView.findViewById(R.id.member_image)

                memberNickname.text = memberList.nickname

                if (!memberList.image.isNullOrEmpty()) {
                    memberImage.setCircleImage(memberList.image)
                }

                memberView.setOnClickListener {
                    toast(R.string.please_sign_up_study)
                }

                binding.memberContainer.addView(memberView)
            }

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().add(R.id.map, it).commit()
                }

            mapFragment.getMapAsync { naverMap ->
                naverMap.cameraPosition = CameraPosition(
                    LatLng(
                        studyDetail.studyInfoItem.locationItem.latitude.toDouble(),
                        studyDetail.studyInfoItem.locationItem.longitude.toDouble()
                    ), 16.0
                )
                naverMap.uiSettings.run {
                    isZoomControlEnabled = false
                    isZoomGesturesEnabled = false
                    isScrollGesturesEnabled = false
                }
            }
        })
    }

    private fun setupView() {
    }

    private fun setupViewModel() {
        with(viewModel) {
            showLinkEvent.observe(this@StudyDetailActivity, EventObserver { url ->
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(this@StudyDetailActivity, Uri.parse(url))
                }
            })
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
                binding.joiningContainer.visibility = View.VISIBLE
            }
            else -> {
                binding.joinButton.visibility = View.VISIBLE
                binding.joiningContainer.visibility = View.INVISIBLE
            }
        }
    }

    private fun showApplyDialog() {
        val dialog = ApplyStudyDialog.newInstance(getString(R.string.apply_title))

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
        private const val KEY_TOOLBAR_TITLE = "TOOLBAR_TITLE"
        private const val DEFAULT_VALUE = -1
        private const val AUTHORITY_APPLIER = "applier"

        fun getIntent(context: Context, studyId: Int, title: String) =
            Intent(context, StudyDetailActivity::class.java)
                .putExtra(STUDY_ID, studyId)
                .putExtra(KEY_TOOLBAR_TITLE, title)
    }
}