package com.iron.espresso.presentation.home.study

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_member.view.member_image
import kotlinx.android.synthetic.main.item_member.view.member_nickname

@AndroidEntryPoint
class StudyDetailActivity :
    BaseActivity<ActivityStudyDetailBinding>(R.layout.activity_study_detail) {

    private val viewModel by viewModels<StudyDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle("스터디 상세화면")
        setNavigationIcon(R.drawable.ic_back_24)

        viewModel.getStudy(2)

        viewModel.studyDetail.observe(this, Observer { studyDetail ->
            binding.introduceDetail.text = studyDetail.introduce
            binding.proceedDetail.text = studyDetail.progress
            binding.timeDetail.text = studyDetail.studyTime
            binding.placeDetail.text = studyDetail.locationResponse?.run {
                "$addressName $placeName"
            }
            binding.numberMember.text = studyDetail.participateResponse?.size.toString()

            Glide.with(this)
                .load(
                    GlideUrl(
                        studyDetail.image,
                        LazyHeaders.Builder()
                            .addHeader("Authorization", AuthHolder.bearerToken)
                            .build()
                    )
                )
                .error(R.drawable.dummy_image)
                .into(binding.image)

            /* 구성원 수 만큼 동적 생성 */
            studyDetail.participateResponse?.forEach { memberList ->
                val memberView = LayoutInflater.from(this)
                    .inflate(R.layout.item_member, binding.memberContainer, false)

                memberView.member_nickname.text = memberList.nickname

                if (memberList.image.isNullOrEmpty()) {
                    memberView.member_image.setImageResource(R.drawable.dummy_image)
                } else {
                    Glide.with(this)
                        .load(
                            GlideUrl(
                                memberList.image,
                                LazyHeaders.Builder()
                                    .addHeader("Authorization", AuthHolder.bearerToken)
                                    .build()
                            )
                        )
                        .apply(RequestOptions.circleCropTransform())
                        .error(R.drawable.dummy_image)
                        .into(memberView.member_image)
                }
                binding.memberContainer.addView(memberView)
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
        fun getInstance(context: Context) =
            Intent(context, StudyDetailActivity::class.java)
    }
}