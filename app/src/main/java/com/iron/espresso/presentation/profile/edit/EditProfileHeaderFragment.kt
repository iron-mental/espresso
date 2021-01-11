package com.iron.espresso.presentation.profile.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.iron.espresso.Logger
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditProfileHeaderBinding
import com.wswon.picker.ImagePickerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileHeaderFragment :
    BaseFragment<FragmentEditProfileHeaderBinding>(R.layout.fragment_edit_profile_header) {

    private val viewModel by viewModels<EditProfileHeaderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle("사진 및 정보 수정")
        binding.viewModel = viewModel

        binding.profileImage.setOnClickListener {
            showImagePicker()
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
                Toast.makeText(requireContext(), "수정 완료 클릭", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_IMAGE_PICKER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri =
                        data?.getParcelableExtra<Uri>(ImagePickerFragment.ARG_IMAGE_URI)
                    Logger.d("$imageUri")
                    binding.profileImage.setImageURI(imageUri)
                }
            }
        }
    }


    private fun showImagePicker() {
        val imagePickerFragment = ImagePickerFragment()
        imagePickerFragment.setTargetFragment(this, REQ_IMAGE_PICKER)
        imagePickerFragment.show(parentFragmentManager, DIALOG_TAG)
    }

    companion object {
        private const val REQ_IMAGE_PICKER = 100
        private const val DIALOG_TAG = "IMAGE_PICKER"

        fun newInstance() =
            EditProfileHeaderFragment()
    }
}