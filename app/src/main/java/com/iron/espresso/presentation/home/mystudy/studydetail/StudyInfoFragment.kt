package com.iron.espresso.presentation.home.mystudy.studydetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.databinding.FragmentStudyInfoBinding

class StudyInfoFragment : BaseFragment<FragmentStudyInfoBinding>(R.layout.fragment_study_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        fun newInstance() =
            StudyInfoFragment()
    }
}