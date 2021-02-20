package com.coding.studymaterials.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.coding.studymaterials.R
import com.coding.studymaterials.activity.BigPicActivity
import com.coding.studymaterials.adapter.GirlAdapter
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.base.LoadMoreOnScrollListener
import com.coding.studymaterials.bean.PaperBean
import com.coding.studymaterials.databinding.FragmentGirlBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author: Coding.He
 * @date: 2020/7/2
 * @emil: 229101253@qq.com
 * @des:
 */
class GirlFragment : BaseFragment() {
    private var isLoadingData = false
    private lateinit var viewBinding: FragmentGirlBinding

    /*获取Girl图片URL的相关参数*/
    private val category = "Girl"
    private val type = "Girl"
    private val count = 10
    private var page = 1

    private val dataList: ArrayList<PaperBean.DataBean> = arrayListOf()
    private lateinit var adapter: GirlAdapter

    override fun bindView(): View {
        viewBinding = FragmentGirlBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun lazyLoadData() {
        viewBinding.srlRefresh.isRefreshing = true
        loadPic(true)
        val spanCount = 2
        viewBinding.rvGirl.layoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        adapter = GirlAdapter(ctx, dataList)
        viewBinding.rvGirl.adapter = adapter
        viewBinding.srlRefresh.setColorSchemeResources(R.color.black, R.color.green, R.color.blue)
    }

    override fun lazyLoadListener() {
        viewBinding.srlRefresh.setOnRefreshListener {
            Log.i(TAG, "重新加载图片")
            loadPic(true)
        }
        adapter.setOnItemClickListener { _, pos ->
            val intent = Intent(activity, BigPicActivity::class.java)
            val bundle = Bundle()
            bundle.putString("name", adapter.getItemData(pos)._id)
            bundle.putString("title", adapter.getItemData(pos).title)
            bundle.putString("desc", adapter.getItemData(pos).desc)
            bundle.putString("time", adapter.getItemData(pos).publishedAt)
            bundle.putString("url", adapter.getItemData(pos).url)
            intent.putExtras(bundle)
            activity?.startActivity(intent)
        }

        viewBinding.rvGirl.addOnScrollListener(object : LoadMoreOnScrollListener() {
            override fun loadMore() {
                if (viewBinding.srlRefresh.isRefreshing) {
                    Log.i(TAG, "刷新中,禁止加载更多数据")
                    return
                }
                loadPic(false)
            }
        })
    }

    @Synchronized
    private fun loadPic(isRefresh: Boolean) {
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
                            Log.i(TAG, "图片添加至list:{${item._id}}")
                            dataList.add(item)
                        } else {
                            Log.i(TAG, "数据中已含有该图片:{${item._id}}")
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