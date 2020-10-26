package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = Button(context).apply {
            text = "스터디 리스트"
            setOnClickListener{
                startActivity(StudyListActivity.getInstance(context))
            }
        }
        (view as ViewGroup).addView(button)
    }

    companion object {
        fun newInstance() =
            StudyFragment()
    }
}



