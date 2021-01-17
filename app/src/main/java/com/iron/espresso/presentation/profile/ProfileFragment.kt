package com.iron.espresso.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.iron.espresso.R
import com.iron.espresso.UserHolder
import com.iron.espresso.base.BaseFragment
import com.iron.espresso.base.MenuSet
import com.iron.espresso.databinding.FragmentProfileBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setCircleImage
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.profile.edit.*
import com.iron.espresso.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseActivity?.setToolbarTitle(R.string.profile_title)

        setProfile()
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.run {
            this.viewModel = this@ProfileFragment.viewModel

            layoutHeader.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                val user = this@ProfileFragment.viewModel.user.value ?: return@setOnClickListener
                showFragment(EditProfileHeaderFragment.newInstance(
                    user.image,
                    user.nickname,
                    user.introduce
                ))
            }
            layoutCareer.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                val user = this@ProfileFragment.viewModel.user.value ?: return@setOnClickListener
                showFragment(EditCareerFragment.newInstance(
                    user.careerTitle,
                    user.careerContents
                ))
            }
            layoutProject.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                showFragment(EditProjectFragment.newInstance())
            }
            layoutSns.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                val user = this@ProfileFragment.viewModel.user.value ?: return@setOnClickListener
                showFragment(EditSnsFragment.newInstance(
                    user.snsGithub,
                    user.snsLinkedin,
                    user.snsWeb
                ))
            }
            layoutEmail.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                showFragment(EditEmailFragment.newInstance())
            }
            layoutArea.root.findViewById<View>(R.id.edt_button).setOnClickListener {
                showFragment(EditAreaFragment.newInstance())
            }
        }
    }

    private fun setupViewModel() {
        viewModel.run {
            showLinkEvent.observe(viewLifecycleOwner, EventObserver { url ->
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(requireContext(), Uri.parse(url))
                }
            })

            projectItemList.observe(viewLifecycleOwner, { projectItemList ->

            })

            refreshed.observe(viewLifecycleOwner, EventObserver {
                setProfile()
            })
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            baseActivity?.setToolbarTitle(R.string.profile_title)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        setMenuItems(menu)
    }

    private fun setMenuItems(menu: Menu) {
        val groupId = 0
        val set = MenuSet.ICON_SHARE

        if (menu.findItem(set.menuId) == null) {
            menu.add(groupId, set.menuId, 0, set.titleResId)
                .setIcon(ContextCompat.getDrawable(requireContext(), set.imageResId))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                baseActivity?.onBackPressed()
            }
            R.id.menu_item_share -> {
                Toast.makeText(requireContext(), "공유하기 클릭", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return true
    }

    private fun setProfile() {
        UserHolder.get()?.let {
            viewModel.setProfile(user = it)

            binding.layoutHeader.profileImage.setCircleImage(it.image)
        }
    }

    private fun showFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            hide(this@ProfileFragment)
            fragment.setTargetFragment(this@ProfileFragment, REQ_MODIFY_SUCCESS_CODE)
            add(R.id.edit_frag_container, fragment, fragment.javaClass.simpleName)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_MODIFY_SUCCESS_CODE && resultCode == Activity.RESULT_OK) {
            toast("수정이 되었다")
            viewModel.refreshProfile()
            setProfile()
        }
    }

    companion object {
        fun newInstance() =
            ProfileFragment()

        private const val REQ_MODIFY_SUCCESS_CODE = 10
    }
}