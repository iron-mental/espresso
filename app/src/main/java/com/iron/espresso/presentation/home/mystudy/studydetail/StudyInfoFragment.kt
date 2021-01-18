package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.iron.espresso.AuthHolder
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyInfoBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_member.view.*

@AndroidEntryPoint
class StudyInfoFragment : BaseFragment<FragmentStudyInfoBinding>(R.layout.fragment_study_info) {

    private val viewModel by viewModels<StudyInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId =
            arguments?.getInt(MyStudyDetailActivity.STUDY_ID) ?: MyStudyDetailActivity.DEFAULT_VALUE
        viewModel.getStudy(studyId)

        viewModel.studyDetail.observe(viewLifecycleOwner, Observer { studyDetail ->
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
                val memberView = LayoutInflater.from(context)
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

            val fm = childFragmentManager
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
                activity?.finish()
            }

            else -> {
                Toast.makeText(context, "${item.title}", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    companion object {
        fun newInstance(studyId: Int) =
            StudyInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MyStudyDetailActivity.STUDY_ID, studyId)
                }
            }
    }
}