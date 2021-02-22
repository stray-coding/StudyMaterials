package com.coding.studymaterials.adapter

import android.content.Context
import android.text.Html
import com.coding.studymaterials.base.BaseRecyclerAdapter
import com.coding.studymaterials.base.RecyclerViewHolder

/**
 * @author: Coding.He
 * @date: 2020/6/19
 * @emil: stray-coding@foxmail.com
 * @des:RecycleView的图片适配器
 */
class AboutAdapter(context: Context, dataList: List<String>) :
    BaseRecyclerAdapter<String>(context, dataList) {
    override fun getItemLayoutId(viewType: Int): Int {
        return android.R.layout.simple_list_item_1
    }

    override fun bindData(
        holder: RecyclerViewHolder,
        position: Int,
        item: String
    ) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.getTextView(android.R.id.text1).text =
                Html.fromHtml(item, Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.getTextView(android.R.id.text1).text = Html.fromHtml(item)
        }
    }
}
