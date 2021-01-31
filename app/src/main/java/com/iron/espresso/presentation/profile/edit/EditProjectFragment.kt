package com.iron.espresso.presentation.profile.edit

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditProjectBinding
import com.iron.espresso.databinding.ViewEditProjectBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.profile.ProjectItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProjectFragment :
    BaseFragment<FragmentEditProjectBinding>(R.layout.fragment_edit_project) {

    private val viewModel by viewModels<EditProjectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle(R.string.title_edit_project)
        binding.viewModel = viewModel

        val list: List<ProjectItem>? = arguments?.getParcelableArrayList(ARG_PROJECT_LIST)
        viewModel.initProjectList(list.orEmpty())

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.run {
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

            firstItem.observe(viewLifecycleOwner, { item ->
                setProjectItem(binding.projectFirst, item)
            })
            secondItem.observe(viewLifecycleOwner, { item ->
                setProjectItem(binding.projectSeconds, item)
            })
            thirdItem.observe(viewLifecycleOwner, { item ->
                setProjectItem(binding.projectThird, item)
            })
        }
    }

    private fun setProjectItem(editProjectBinding: ViewEditProjectBinding, item: ProjectItem) {
        with(editProjectBinding) {
            this.item = item
            title.setText(item.title)
            contents.setText(item.contents)
            githubInputView.inputUrl.setText(item.githubUrl)
            appstoreInputView.inputUrl.setText(item.appStoreUrl)
            playstoreInputView.inputUrl.setText(item.playStoreUrl)
        }
    }

    private fun getModifyItem(editProjectBinding: ViewEditProjectBinding): ProjectItem =
        ProjectItem(
            id = editProjectBinding.item?.id ?: -1,
            title = editProjectBinding.title.text.toString(),
            contents = editProjectBinding.contents.text.toString(),
            githubUrl = editProjectBinding.githubInputView.inputUrl.text.toString(),
            appStoreUrl = editProjectBinding.appstoreInputView.inputUrl.text.toString(),
            playStoreUrl = editProjectBinding.playstoreInputView.inputUrl.text.toString()
        )

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

                val list = listOf(
                    getModifyItem(binding.projectFirst),
                    getModifyItem(binding.projectSeconds),
                    getModifyItem(binding.projectThird)
                ).filter { !it.isEmptyItem() }
                viewModel.modifyInfo(list)
            }
            else -> {

            }
        }
        return true
    }

    companion object {
        private const val ARG_PROJECT_LIST = "project_list"

        fun newInstance(projectList: List<ProjectItem>) =
            EditProjectFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PROJECT_LIST, ArrayList(projectList))
                }
            }
    }
}