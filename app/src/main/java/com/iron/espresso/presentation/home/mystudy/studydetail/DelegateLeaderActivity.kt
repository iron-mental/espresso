package com.iron.espresso.presentation.home.mystudy.studydetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import com.iron.espresso.DEF_VALUE
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.databinding.ActivityDelegateLeaderBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.apply.ConfirmDialog
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.mystudy.adapter.ParticipateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DelegateLeaderActivity :
    BaseActivity<ActivityDelegateLeaderBinding>(R.layout.activity_delegate_leader) {

    private val viewModel by viewModels<DelegateLeaderViewModel>()
    private val participateAdapter = ParticipateAdapter()
    private var studyId = -1
    private var memberList = listOf<ParticipateItem>()
    private var authority: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_back_24)
        setToolbarTitle(TOOLBAR_TITLE)

        studyId =
            intent.getIntExtra(MyStudyDetailActivity.STUDY_ID, DEF_VALUE)
        memberList = intent.getSerializableExtra(PARTICIPATE_LIST) as List<ParticipateItem>
        authority = intent.getStringExtra(KEY_AUTHORITY)

        binding.participateList.adapter = participateAdapter

        participateAdapter.apply {
            setItemList(excludeOneself(memberList))
            setItemClickListener { participateItem ->
                showDelegateLeaderDialog(participateItem.userId)
            }
        }

        viewModel.run {
            successEvent.observe(this@DelegateLeaderActivity, EventObserver { isSuccess ->
                if (isSuccess) {
                    toast(R.string.success_delegate)
                    setResult(RESULT_OK)
                    finish()
                }
            })

            toastMessage.observe(this@DelegateLeaderActivity, EventObserver(::toast))
        }
    }

    private fun excludeOneself(memberList: List<ParticipateItem>): List<ParticipateItem> {
        return memberList.filter {
            !it.leader
        }
    }

    private fun showDelegateLeaderDialog(userId: Int) {
        val dialog = ConfirmDialog.newInstance(getString(R.string.dialog_delegate_leader_title))

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)

        dialog.setFragmentResultListener(dialog::class.java.simpleName) { _: String, bundle: Bundle ->
            val result = bundle.get(ConfirmDialog.RESULT)

            if (result == Activity.RESULT_OK) {
                viewModel.delegateStudyLeader(studyId, userId)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {
        private const val TOOLBAR_TITLE = "스터디 장 위임화면"
        private const val PARTICIPATE_LIST = "participateList"
        private const val KEY_AUTHORITY = "AUTHORITY"
        fun getIntent(context: Context, studyId: Int, participateList: ArrayList<ParticipateItem>, authority: String) =
            Intent(context, DelegateLeaderActivity::class.java)
                .putExtra(MyStudyDetailActivity.STUDY_ID, studyId)
                .putExtra(PARTICIPATE_LIST, participateList)
                .putExtra(KEY_AUTHORITY, authority)
    }
}