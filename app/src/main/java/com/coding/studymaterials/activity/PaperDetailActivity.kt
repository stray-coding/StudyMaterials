package com.coding.studymaterials.activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import com.coding.studymaterials.base.BaseActivity
import com.coding.studymaterials.bean.PaperDetailBean
import com.coding.studymaterials.databinding.ActivityPaperDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class PaperDetailActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityPaperDetailBinding

    override fun bindView(): View {
        viewBinding = ActivityPaperDetailBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initDataBeforeSetContentView(savedInstanceState: Bundle?) {

    }

    override fun initDataAfterSetContentView(savedInstanceState: Bundle?) {
        if (intent != null && intent.extras != null) {
            val id = intent.extras!!.getString("name", "")
            if (id.isNotEmpty()) {
                getPaperDetail(id)
            }
        }
    }

    override fun setListener() {

    }

    @Synchronized
    private fun getPaperDetail(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gank.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val request = retrofit.create(GetPaperDetail::class.java)
        val call = request.getCall("v2/post/5e7225672b34a0e184e1e4b9")
        call.enqueue(object : Callback<PaperDetailBean> {
            override fun onResponse(
                call: Call<PaperDetailBean>,
                response: Response<PaperDetailBean>
            ) {
                Log.i(TAG, "response: {$response}")
                Log.i(TAG, "response.body(): {${response.body()}}")
                val body = response.body() as PaperDetailBean
                showTip(body.data.title)
                viewBinding.paperTitle.text = body.data.title
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    viewBinding.paperContent.text =
                        Html.fromHtml(body.data.content, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    viewBinding.paperContent.text = Html.fromHtml(body.data.content)
                }
            }

            override fun onFailure(call: Call<PaperDetailBean>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
                showTip("网络错误，加载失败")
            }
        })
    }

    interface GetPaperDetail {
        @GET
        fun getCall(@Url url: String): Call<PaperDetailBean>
    }
}