package com.iron.espresso.presentation.home.mystudy.studydetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyInfoBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.setRadiusImage
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyInfoFragment : BaseFragment<FragmentStudyInfoBinding>(R.layout.fragment_study_info) {

    private val studyInfoViewModel by viewModels<StudyInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId =
            arguments?.getInt(MyStudyDetailActivity.STUDY_ID) ?: MyStudyDetailActivity.DEFAULT_VALUE
        studyInfoViewModel.getStudy(studyId)

        studyInfoViewModel.studyDetail.observe(viewLifecycleOwner, Observer { studyDetail ->
            binding.run {
                viewModel = studyInfoViewModel
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

        studyInfoViewModel.showLinkEvent.observe(viewLifecycleOwner, EventObserver { url ->
            if (url.startsWith("http://") || url.startsWith("https://")) {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(requireContext(), Uri.parse(url))
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