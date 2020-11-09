package com.iron.espresso.presentation.home.mystudy.studydetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.gson.annotations.SerializedName
import com.iron.espresso.R
import com.iron.espresso.ToolbarHelper
import com.iron.espresso.data.model.NoticeItemType
import com.iron.espresso.data.model.NoticeListItem
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
    @SerializedName("id")
    val id: Int?,
    @SerializedName("study_id")
    val studyId: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("pinned")
    val pinned: Boolean?,
    @SerializedName("created_at")
    val createAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)


class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeDetailBinding
    private lateinit var toolbarHelper: ToolbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_detail)

        toolbarHelper = ToolbarHelper(this, binding.appbar).apply {
            setTitle(TOOLBAR_TITLE)
            setNavigationIcon(R.drawable.ic_back_24)
        }

        binding.category.apply {
            when (intent.extras?.get("type")) {
                NoticeItemType.HEADER -> {
                    text = resources.getString(R.string.pined_true)
                    setBackgroundResource(R.color.theme_fc813e)
                }
                NoticeItemType.ITEM -> {
                    text = resources.getString(R.string.pined_false)
                    setBackgroundResource(R.color.colorCobaltBlue)
                }
                else -> error("error")
            }
        }

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
                Log.d("TAG","response : ${response.body()!!.studyId}")
                Log.d("TAG","response : ${response.errorBody()}")
                Log.d("TAG","response : ${response.message()}")
                Log.d("TAG","response : ${response.code()}")
                Log.d("TAG", "성공 : ${response.raw()}")
                if (response.isSuccessful) {
                    Log.d("Response :: ", "${response.body()}")
                } else {
                    Log.d("Response :: ", "실패")
                }
                val data: NoticeResponse? = response.body()
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
        fun getInstance(context: Context, noticeItem: NoticeListItem) =
            Intent(context, NoticeDetailActivity::class.java).putExtra("type", noticeItem.type)
    }
}