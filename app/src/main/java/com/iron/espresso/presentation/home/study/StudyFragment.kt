package com.iron.espresso.presentation.home.study

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iron.espresso.R
import com.iron.espresso.databinding.FragmentStudyBinding
import com.iron.espresso.utils.ToolbarHelper

class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding

    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarHelper = ToolbarHelper((activity as AppCompatActivity), binding.appbar).apply {
            setTitle(TOOLBAR_TITLE)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_study, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_study -> {
            }

            R.id.add_study -> {
            }

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        private const val TOOLBAR_TITLE = "스터디"

        fun newInstance() =
            StudyFragment()
    }
}



