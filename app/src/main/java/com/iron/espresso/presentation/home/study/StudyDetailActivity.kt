package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.iron.espresso.ext.setCircleImage
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

        viewModel.studyDetail.observe(this, Observer { studyDetail ->
            binding.run {
                introduceDetail.text = studyDetail.introduce
                proceedDetail.text = studyDetail.progress
                timeDetail.text = studyDetail.studyTime
                placeDetail.text = studyDetail.locationItem.run {
                    "$addressName $placeName"
                }
                numberMember.text = studyDetail.participateItem.size.toString()

                if (studyDetail.image.isNotEmpty()) {
                    image.setCircleImage(studyDetail.image)
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

            val fm = supportFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.map, it).commit()
                }

            mapFragment.getMapAsync { naverMap ->
                naverMap.cameraPosition = CameraPosition(
                    LatLng(
                        studyDetail.locationItem.longitude.toDouble(),
                        studyDetail.locationItem.latitude.toDouble()
                    ), 16.0
                )
            }
        })

        binding.joinButton.setOnClickListener {
            Toast.makeText(this, "신청", Toast.LENGTH_SHORT).show()
            viewModel.registerApply(1, "스터디 신청 메세지 테스트")
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

        fun getInstance(context: Context, studyId: Int) =
            Intent(context, StudyDetailActivity::class.java)
                .putExtra(STUDY_ID, studyId)
    }
}