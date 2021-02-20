package com.coding.studymaterials.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.coding.studymaterials.R
import com.coding.studymaterials.base.BaseRecyclerAdapter
import com.coding.studymaterials.base.RecyclerViewHolder
import com.coding.studymaterials.bean.PaperBean


/**
 * @author: Coding.He
 * @date: 2020/6/19
 * @emil: 229101253@qq.com
 * @des:RecycleView的图片适配器
 */
class PaperAdapter(context: Context, dataList: List<PaperBean.DataBean>) :
    BaseRecyclerAdapter<PaperBean.DataBean>(context, dataList) {

    override fun getItemLayoutId(viewType: Int): Int {
        return when (viewType) {
            RecyclerViewType.CONTENT.value -> R.layout.rv_item_paper
            RecyclerViewType.FOOTER.value -> R.layout.foot_load_view
            else -> R.layout.rv_item_paper
        }
    }

    override fun bindData(
        holder: RecyclerViewHolder,
        position: Int,
        item: PaperBean.DataBean
    ) {
        if (getItemViewType(position) == RecyclerViewType.CONTENT.value) {
            val img = holder.getImageView(R.id.img_paper)
            if (item.images.isNotEmpty()) {
                Glide.with(mContext)
                    .load(item.images[0])
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(img)
            }
            holder.setText(R.id.tv_paper_title, item.title)
            holder.setText(R.id.tv_paper_content, item.desc)
            holder.setText(R.id.tv_paper_author, item.author)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (!isFooter(position)) {
            bindData(holder, position, mData[position])
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (isFooter(position)) {
            RecyclerViewType.FOOTER.value
        } else {
            RecyclerViewType.CONTENT.value
        }
    }

    override fun getItemCount(): Int {
        return if (mData.size == 0) 0 else mData.size + 1
    }

    private fun isFooter(pos: Int): Boolean {
        return pos == itemCount - 1
    }
}
