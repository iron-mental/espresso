package com.iron.espresso.presentation.profile.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditProfileHeaderBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.checkReadStoragePermission
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.toast
import com.wswon.picker.ImagePickerFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileHeaderFragment :
    BaseFragment<FragmentEditProfileHeaderBinding>(R.layout.fragment_edit_profile_header) {

    private val viewModel by viewModels<EditProfileHeaderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle("사진 및 정보 수정")

        arguments?.let {args ->
            binding.profileImage.setCircleImage(args.getString(ARG_IMAGE_URL).orEmpty())
            viewModel.nickname.value = args.getString(ARG_NICKNAME)
            viewModel.introduce.value = args.getString(ARG_INTRODUCE)
        }

        binding.run {
            this.viewModel = this@EditProfileHeaderFragment.viewModel

            profileImage.setOnClickListener {
                checkReadStoragePermission(requireContext()) { isSuccess ->
                    if (isSuccess) {
                        showImagePicker()
                    }
                }
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner, EventObserver {
            toast(it)
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_IMAGE_PICKER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri =
                        data?.getParcelableExtra<Uri>(ImagePickerFragment.ARG_IMAGE_URI)

                    binding.profileImage.setImageURI(imageUri)

                    if (imageUri != null) {
                        viewModel.setImageUri(imageUri)
                    }
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

        private const val ARG_IMAGE_URL = "arg_image_url"
        private const val ARG_NICKNAME = "arg_nickname"
        private const val ARG_INTRODUCE = "arg_introduce"

        fun newInstance(
            imageUrl: String,
            nickname: String,
            introduce: String
        ) =
            EditProfileHeaderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, imageUrl)
                    putString(ARG_NICKNAME, nickname)
                    putString(ARG_INTRODUCE, introduce)
                }
            }
    }
}