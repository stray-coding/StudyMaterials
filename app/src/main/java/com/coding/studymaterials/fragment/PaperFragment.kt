package com.coding.studymaterials.fragment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.studymaterials.R
import com.coding.studymaterials.adapter.PaperAdapter
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.base.LoadMoreOnScrollListener
import com.coding.studymaterials.bean.PaperBean
import com.coding.studymaterials.databinding.FragmentPaperBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author: Coding.He
 * @date: 2021/2/20
 * @emil: 229101253@qq.com
 * @des:
 */
class PaperFragment private constructor() : BaseFragment() {
    companion object {
        fun newInstance(type: String): PaperFragment {
            val fragment = PaperFragment()
            fragment.type = type
            return fragment
        }
    }

    /*获取Article中的文章*/
    private val category = "GanHuo"
    private var type: String = ""
    private val count = 10
    private var page = 1

    private var isLoadingData = false
    private val dataList: ArrayList<PaperBean.DataBean> = arrayListOf()
    private lateinit var adapter: PaperAdapter
    private lateinit var viewBinding: FragmentPaperBinding
    override fun bindView(): View {
        viewBinding = FragmentPaperBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun lazyLoadData() {
        viewBinding.srlRefresh.isRefreshing = true
        loadPaper(true)
        viewBinding.rvPaper.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = PaperAdapter(ctx, dataList)
        viewBinding.rvPaper.adapter = adapter
        viewBinding.srlRefresh.setColorSchemeResources(R.color.black, R.color.green, R.color.blue)
    }

    override fun lazyLoadListener() {
        viewBinding.srlRefresh.setOnRefreshListener {
            Log.i(TAG, "重新加载图片")
            loadPaper(true)
        }

        viewBinding.rvPaper.addOnScrollListener(object : LoadMoreOnScrollListener() {
            override fun loadMore() {
                if (viewBinding.srlRefresh.isRefreshing) {
                    Log.i(TAG, "刷新中,禁止加载更多数据")
                    return
                }
                loadPaper(false)
            }
        })

    }

    @Synchronized
    private fun loadPaper(isRefresh: Boolean) {
        if (isLoadingData) {
            Log.i(TAG, "数据正在加载中，请勿重复刷新")
            return
        }
        try {
            isLoadingData = true
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val request = retrofit.create(GetOnePic::class.java)
            if (isRefresh) page = 1 else page++
            val url = getRequestUrl(category, type, page, count)
            val call = request.getCall(url)
            call.enqueue(object : Callback<PaperBean> {
                override fun onResponse(
                    call: Call<PaperBean>,
                    response: Response<PaperBean>
                ) {
                    Log.i(TAG, "response: {$response}")
                    Log.i(TAG, "response.body(): {${response.body()}}")
                    val body = response.body() as PaperBean
                    if (isRefresh) {
                        dataList.clear()
                    }
                    for (item in body.data) {
                        if (!dataList.contains(item)) {
                            dataList.add(item)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    viewBinding.srlRefresh.isRefreshing = false
                    isLoadingData = false
                }

                override fun onFailure(call: Call<PaperBean>, t: Throwable) {
                    Log.e(TAG, "onFailure$t")
                    showTip("数据加载失败")
                    viewBinding.srlRefresh.isRefreshing = false
                    isLoadingData = false
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "e:$e")
            showTip("数据加载失败")
            viewBinding.srlRefresh.isRefreshing = false
            isLoadingData = false
        }
    }

    interface GetOnePic {
        @GET
        fun getCall(@Url url: String): Call<PaperBean>
    }


    private fun getRequestUrl(category: String, type: String, page: Int, count: Int): String {
        return "v2/data/category/$category/type/$type/page/$page/count/$count"
    }
}