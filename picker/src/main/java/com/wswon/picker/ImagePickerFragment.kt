package com.wswon.picker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.wswon.picker.adapter.SelectorRvAdapter
import com.wswon.picker.databinding.FragmentImagePickerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentImagePickerBinding

    private val viewModel: ImagePickerViewModel by viewModels()

    private val selectorAdapter by lazy {
        SelectorRvAdapter(
            onItemClick = { position ->
                showImageDetail(position)
            },
            onItemSelected = itemSelected
        )
    }

    private val itemSelected: (position: Int, isSelected: Boolean) -> Unit  = { position, isSelected ->
        if (isSelected) {
            viewModel.addSelectedItemPosition(position)
        } else {
            viewModel.removeSelectedItemPosition(position)
        }
    }

    private lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image_picker, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        view?.run {
            post {
                val parent = parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                val bottomSheetBehavior = behavior as BottomSheetBehavior
                val height = getBottomSheetDialogDefaultHeight()

                if (height != 0) {
                    bottomSheetBehavior.peekHeight = height
                }
            }
        }
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() / 2
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.run {
            vm = viewModel

            rlToolBar.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            loadingPanel.run {
                layoutParams = layoutParams.apply {
                    height = getBottomSheetDialogDefaultHeight() - rlToolBar.measuredHeight
                }
            }

            btnDone.setOnClickListener {
                val selectedImageUriList = viewModel.getItemUriList()

                selectedImageUriList?.firstOrNull()?.let {
                    startActivityForResult(ImageCropActivity.getIntent(requireContext(), it), CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
                }
            }
            tvFolderName.setOnClickListener {
                if (::popupMenu.isInitialized) {
                    popupMenu.show()
                }
            }
            rvImageSelector.adapter = selectorAdapter
            rvImageSelector.addItemDecoration(GridDividerDecoration())

            viewModel.loadImageUri()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                val resultUri = data?.getParcelableExtra(CropImage.CROP_IMAGE_EXTRA_RESULT) as? Uri

                val uriListData = Intent().apply {
                    putExtra(ARG_IMAGE_URI, arrayOf(resultUri))
                }
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    uriListData
                )

                dismiss()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }
    }

    private fun initViewModel() {
        viewModel.run {
            folderNames.observe(viewLifecycleOwner, Observer { countFolderNames ->
                if (countFolderNames.isNotEmpty()) {
                    setFolderPopupMenu(countFolderNames)
                }
            })
            selectedPositionList.observe(viewLifecycleOwner, Observer {
                selectorAdapter.replaceSelectedPositionList(it)
            })
            loadFailedItemPosition.observe(viewLifecycleOwner, Observer { position ->
                selectorAdapter.setItemLoadFailed(position)
            })
        }
    }


    private fun setFolderPopupMenu(folderNames: List<String>) {
        popupMenu = PopupMenu(requireContext(), binding.tvFolderName)

        popupMenu.menu.add(getString(R.string.pic_all))
        folderNames.forEach { folderName ->
            popupMenu.menu.add(folderName)
        }

        activity?.menuInflater?.inflate(R.menu.menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            if (binding.tvFolderName.text != it.title) {
                binding.tvFolderName.text = it.title
                viewModel.clearSelectedItemPosition()
                viewModel.changeFolder(removeTitleCount(it.title.toString()))
            }
            binding.rvImageSelector.scrollToPosition(0)
            true
        }
    }

    private fun removeTitleCount(title: String) =
        if (title == getString(R.string.pic_all)) {
            ""
        } else {
            title.split(" ").dropLast(1).joinToString(" ")
        }


    private fun showImageDetail(position: Int) {
        startActivity(
            ImageDetailActivity.getIntent(
                requireContext(),
                removeTitleCount(binding.tvFolderName.text.toString()),
                position
            )
        )
    }

    companion object {
        const val ARG_IMAGE_URI = "ARG_IMAGE_URI"
    }
}