package com.iron.espresso.presentation.profile.edit

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditCareerBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCareerFragment :
    BaseFragment<FragmentEditCareerBinding>(R.layout.fragment_edit_career) {

    private val viewModel by viewModels<EditCareerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle(R.string.title_edit_career)

        arguments?.let { args ->
            viewModel.initProfileData(
                args.getString(ARG_TITLE).orEmpty(),
                args.getString(ARG_CONTENTS).orEmpty()
            )
        }

        binding.viewModel = viewModel
        setupViewModel()
    }

    private fun setupViewModel() {

        viewModel.run {
            successEvent.observe(viewLifecycleOwner, EventObserver { isSuccess ->
                if (isSuccess) {
                    toast(R.string.success_modify)
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
                    activity?.onBackPressed()
                } else {
                    activity?.onBackPressed()
                }
            })

            toastMessage.observe(viewLifecycleOwner, EventObserver {
                toast(it)
            })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        setMenuItems(menu)
    }

    private fun setMenuItems(menu: Menu) {
        val groupId = 0
        val set = MenuSet.ICON_DONE

        if (menu.findItem(set.menuId) == null) {
            menu.add(groupId, set.menuId, 0, set.titleResId)
                .setIcon(ContextCompat.getDrawable(requireContext(), set.imageResId))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_done -> {
                viewModel.modifyInfo()
            }
            else -> {

            }
        }
        return true
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENTS = "arg_contents"

        fun newInstance(title: String, contents: String) =
            EditCareerFragment().apply {
                arguments = bundleOf(
                    ARG_TITLE to title,
                    ARG_CONTENTS to contents
                )
            }
    }
}
