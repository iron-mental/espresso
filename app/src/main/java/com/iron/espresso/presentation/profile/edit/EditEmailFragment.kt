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
import com.iron.espresso.databinding.FragmentEditEmailBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEmailFragment :
    BaseFragment<FragmentEditEmailBinding>(R.layout.fragment_edit_email) {

    private val viewModel by viewModels<EditEmailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle(R.string.title_edit_email)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.viewModel = viewModel
    }

    private fun setupViewModel() {
        viewModel.run {
            arguments?.let { args ->
                val email = args.getString(ARG_EMAIL).orEmpty()
                initProfileData(email)
            }

            successEvent.observe(viewLifecycleOwner, EventObserver { isSuccess ->
                if (isSuccess) {
                    toast(R.string.success_modify)
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
                }
                activity?.onBackPressed()
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
        private const val ARG_EMAIL = "arg_email"

        fun newInstance(email: String) =
            EditEmailFragment().apply {
                arguments = bundleOf(
                    ARG_EMAIL to email
                )
            }
    }
}