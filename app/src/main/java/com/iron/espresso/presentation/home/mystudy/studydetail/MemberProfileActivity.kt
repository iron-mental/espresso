package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityMemberProfileBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.setLoading
import com.iron.espresso.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberProfileActivity : BaseActivity<ActivityMemberProfileBinding>(R.layout.activity_member_profile) {

    private val memberProfileViewModel by viewModels<MemberProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(intent.getStringExtra(KEY_USER_NICKNAME))
        setNavigationIcon(R.drawable.ic_back_24)

        setupView()
        setupViewModel()
        memberProfileViewModel.getMemberDetail()
    }

    private fun setupView() {
        with(binding) {
            viewModel = memberProfileViewModel
        }
    }

    private fun setupViewModel() {
        with(memberProfileViewModel) {
            showLinkEvent.observe(this@MemberProfileActivity, EventObserver { url ->
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    androidx.browser.customtabs.CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(this@MemberProfileActivity, android.net.Uri.parse(url))
                }
            })
            toastMessage.observe(this@MemberProfileActivity, EventObserver(::toast))
            loadingState.observe(this@MemberProfileActivity, EventObserver(::setLoading))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_USER_NICKNAME = "NICKNAME"
        fun getIntent(context: Context, userId: Int, nickname: String) =
            Intent(context, MemberProfileActivity::class.java)
                .putExtra(MemberProfileViewModel.KEY_USER_ID, userId)
                .putExtra(KEY_USER_NICKNAME, nickname)
    }
}