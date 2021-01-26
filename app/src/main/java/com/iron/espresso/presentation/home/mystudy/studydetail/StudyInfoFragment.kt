package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyInfoBinding
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.setRadiusImage
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyInfoFragment : BaseFragment<FragmentStudyInfoBinding>(R.layout.fragment_study_info) {

    private val viewModel by viewModels<StudyInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId =
            arguments?.getInt(MyStudyDetailActivity.STUDY_ID) ?: MyStudyDetailActivity.DEFAULT_VALUE
        viewModel.getStudy(studyId)

        viewModel.studyDetail.observe(viewLifecycleOwner, Observer { studyDetail ->
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
                val memberView = LayoutInflater.from(context)
                    .inflate(R.layout.item_member, binding.memberContainer, false)

                val memberNickname: TextView = memberView.findViewById(R.id.member_nickname)
                val memberImage: ImageView = memberView.findViewById(R.id.member_image)

                memberNickname.text = memberList.nickname

                if (!memberList.image.isNullOrEmpty()) {
                    memberImage.setCircleImage(memberList.image)
                }

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