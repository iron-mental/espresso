package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.databinding.ActivityDelegateLeaderBinding
import com.iron.espresso.ext.EventObserver
import com.iron.espresso.ext.toast
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity
import com.iron.espresso.presentation.home.mystudy.adapter.ParticipateAdapter

class DelegateLeaderActivity :
    BaseActivity<ActivityDelegateLeaderBinding>(R.layout.activity_delegate_leader) {

    private val viewModel by viewModels<DelegateLeaderViewModel>()
    private val participateAdapter = ParticipateAdapter()
    private var studyId = -1
    private var memberList = listOf<ParticipateItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavigationIcon(R.drawable.ic_back_24)
        setToolbarTitle(TOOLBAR_TITLE)

        studyId =
            intent.getIntExtra(MyStudyDetailActivity.STUDY_ID, MyStudyDetailActivity.DEFAULT_VALUE)
        memberList = intent.getSerializableExtra(PARTICIPATE_LIST) as List<ParticipateItem>

        binding.participateList.adapter = participateAdapter

        participateAdapter.apply {
            setItemList(memberList)
            setItemClickListener { participateItem ->
                viewModel.delegateStudyLeader(
                    studyId,
                    participateItem.userId
                )
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
        fun getIntent(context: Context, studyId: Int, participateList: ArrayList<ParticipateItem>) =
            Intent(context, DelegateLeaderActivity::class.java)
                .putExtra(MyStudyDetailActivity.STUDY_ID, studyId)
                .putExtra(PARTICIPATE_LIST, participateList)
    }
}