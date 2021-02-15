package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iron.espresso.R
import com.iron.espresso.ValidationInputText
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.setRadiusImage
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyDetailActivity :
    BaseActivity<ActivityStudyDetailBinding>(R.layout.activity_study_detail) {

    private val viewModel by viewModels<StudyDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("스터디 상세화면")
        setNavigationIcon(R.drawable.ic_back_24)

        val studyId = intent.getIntExtra(STUDY_ID, DEFAULT_VALUE)
        viewModel.getStudy(studyId)

        binding.joinButton.setOnClickListener {
            showApplyDialog(studyId)
        }

        viewModel.emptyCheckMessage.observe(this, EventObserver {
            Toast.makeText(this, resources.getString(it.resId), Toast.LENGTH_SHORT).show()
            if (it == ValidationInputText.SUCCESS) {
                visibleBtn(AUTHORITY_APPLIER)
            }
        })

        viewModel.toastMessage.observe(this, EventObserver {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.studyDetail.observe(this, Observer { studyDetail ->
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

                binding.memberContainer.addView(memberView)
            }

            val fm = supportFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.map, it).commit()
                }

            mapFragment.getMapAsync { naverMap ->
                naverMap.cameraPosition = CameraPosition(
                    LatLng(
                        studyDetail.studyInfoItem.locationItem.longitude.toDouble(),
                        studyDetail.studyInfoItem.locationItem.latitude.toDouble()
                    ), 16.0
                )
            }
        })
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

    private fun showApplyDialog(studyId: Int) {
        val applyDialog = layoutInflater.inflate(R.layout.view_apply_study, null)
        val messageInputView: EditText = applyDialog.findViewById(R.id.message_input_view)

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.apply_title))
            .setMessage(resources.getString(R.string.apply_content))
            .setView(applyDialog)
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.apply)) { _, _ ->
                viewModel.registerApply(studyId, "${messageInputView.text}")
            }
            .show()
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