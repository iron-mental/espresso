package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.AuthHolder
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityStudyDetailBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_member.view.member_image

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
            binding.introduceDetail.text = studyDetail.introduce
            binding.proceedDetail.text = studyDetail.progress
            binding.timeDetail.text = studyDetail.studyTime
            binding.placeDetail.text = studyDetail.locationItem.run {
                "$addressName $placeName"
            }
            binding.numberMember.text = studyDetail.participateItem.size.toString()

            Glide.with(this)
                .load(
                    if (studyDetail.image.isNullOrEmpty()) {
                        R.drawable.dummy_image
                    } else {
                        GlideUrl(
                            studyDetail.image,
                            LazyHeaders.Builder()
                                .addHeader("Authorization", AuthHolder.bearerToken)
                                .build()
                        )
                    }
                )
                .error(R.drawable.dummy_image)
                .into(binding.image)

            /* 구성원 수 만큼 동적 생성 */
            studyDetail.participateItem.forEach { memberList ->
                val memberView = LayoutInflater.from(this)
                    .inflate(R.layout.item_member, binding.memberContainer, false)

                memberView.findViewById<TextView>(R.id.member_nickname).text = memberList.nickname

                Glide.with(this)
                    .load(
                        if (memberList.image.isNullOrEmpty()) {
                            R.drawable.dummy_image
                        } else {
                            GlideUrl(
                                memberList.image,
                                LazyHeaders.Builder()
                                    .addHeader("Authorization", AuthHolder.bearerToken)
                                    .build()
                            )
                        }
                    )
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.dummy_image)
                    .into(memberView.member_image)

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