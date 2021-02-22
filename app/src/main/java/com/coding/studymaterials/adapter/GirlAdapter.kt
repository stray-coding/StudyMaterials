package com.coding.studymaterials.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.coding.studymaterials.R
import com.coding.studymaterials.base.BaseRecyclerAdapter
import com.coding.studymaterials.base.RecyclerViewHolder
import com.coding.studymaterials.bean.PaperBean


/**
 * @author: Coding.He
 * @date: 2020/6/19
 * @emil: stray-coding@foxmail.com
 * @des:RecycleView的图片适配器
 */
class GirlAdapter(context: Context, dataList: List<PaperBean.DataBean>) :
    BaseRecyclerAdapter<PaperBean.DataBean>(context, dataList) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        when (val manager = recyclerView.layoutManager as RecyclerView.LayoutManager) {
            is GridLayoutManager -> manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isFooter(position)) {
                        manager.spanCount
                    } else 1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (isFooter(holder.layoutPosition)) {
            when (val p = holder.itemView.layoutParams) {
                is StaggeredGridLayoutManager.LayoutParams -> p.isFullSpan = true
            }
        }
    }

    override fun getItemLayoutId(viewType: Int): Int {
        return when (viewType) {
            RecyclerViewType.CONTENT.value -> R.layout.rv_item_girl
            RecyclerViewType.FOOTER.value -> R.layout.foot_load_view
            else -> R.layout.rv_item_girl
        }
    }

    override fun bindData(
        holder: RecyclerViewHolder,
        position: Int,
        item: PaperBean.DataBean
    ) {
        if (getItemViewType(position) == RecyclerViewType.CONTENT.value) {
            val img = holder.getImageView(R.id.img_girl)
            if (item.images.isNotEmpty()) {
                Glide.with(mContext)
                    .load(item.images[0])
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(img)
            }
            holder.setText(R.id.tv_msg, item.desc)
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
