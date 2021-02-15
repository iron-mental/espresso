package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.data.model.ParticipateItem
import com.iron.espresso.databinding.ActivityDelegateLeaderBinding
import com.iron.espresso.presentation.home.mystudy.MyStudyDetailActivity

class DelegateLeaderActivity :
    BaseActivity<ActivityDelegateLeaderBinding>(R.layout.activity_delegate_leader) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val PARTICIPATE_LIST = "participateList"
        fun getIntent(context: Context, studyId: Int, participateList: ArrayList<ParticipateItem>) =
            Intent(context, DelegateLeaderActivity::class.java)
                .putExtra(MyStudyDetailActivity.STUDY_ID, studyId)
                .putExtra(PARTICIPATE_LIST, participateList)
    }
}