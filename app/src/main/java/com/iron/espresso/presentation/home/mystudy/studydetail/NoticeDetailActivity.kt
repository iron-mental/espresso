package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.gson.annotations.SerializedName
import com.iron.espresso.R
import com.iron.espresso.base.BaseActivity
import com.iron.espresso.databinding.ActivityNoticeDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitNetwork {
    @GET("/v1/study/{study_id}/notice/{notice_id}")
    fun getTest(
        @Path("study_id") studyId: Int,
        @Path("notice_id") noticeId: Int
    ): Call<NoticeResponse>
}

data class NoticeResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("data")
    val data: Notice,
)

data class Notice(
    @SerializedName("id")
    val id: Int,
    @SerializedName("study_id")
    val studyId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("pinned")
    val pinned: Boolean,
    @SerializedName("created_at")
    val createAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)


class NoticeDetailActivity :
    BaseActivity<ActivityNoticeDetailBinding>(R.layout.activity_notice_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(TOOLBAR_TITLE)
        setNavigationIcon(R.drawable.ic_back_24)

        //retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.35.154.27:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitNetwork::class.java)
        val callGetTest = service.getTest(1, 1)

        callGetTest.enqueue(object : Callback<NoticeResponse> {
            override fun onResponse(
                call: Call<NoticeResponse>,
                response: Response<NoticeResponse>
            ) {
                Log.d("TAG", "성공 : ${response.raw()}")
                Log.d("Response :: ", "${response.body()}")

                val data = response.body()?.data?.pinned ?: return

                binding.category.apply {
                    if (data) {
                        text = resources.getString(R.string.pined_true)
                        setBackgroundResource(R.color.theme_fc813e)
                    } else {
                        text = resources.getString(R.string.pined_false)
                        setBackgroundResource(R.color.colorCobaltBlue)
                    }
                }
            }

            override fun onFailure(call: Call<NoticeResponse>, t: Throwable) {
                Log.d("TAG", "실패 : $t")
            }
        })
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
        const val TOOLBAR_TITLE = "공지사항 상세 화면"
        fun getInstance(context: Context) =
            Intent(context, NoticeDetailActivity::class.java)
    }
}