package com.iron.espresso.presentation.profile.edit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentEditCareerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCareerFragment :
    BaseFragment<FragmentEditCareerBinding>(R.layout.fragment_edit_career) {

    private val viewModel by viewModels<EditCareerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle("경력 수정")

        binding.viewModel = viewModel
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

    companion object {
        fun newInstance() =
            EditCareerFragment()
    }
}
