package com.coding.studymaterials.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.studymaterials.adapter.AboutAdapter
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.databinding.FragmentAboutBinding
import com.coding.studymaterials.divider.RecycleViewDivider

/**
 * @author: Coding.He
 * @date: 2020/7/2
 * @emil: 229101253@qq.com
 * @des:
 */
class AboutFragment : BaseFragment() {
    private val lists = listOf(
        "author：stray-coding",
        "mail：stray-coding@foxmail.com",
        "GitHub：https://github.com/stray-coding/StudyMaterials"
    )
    private lateinit var adapter: AboutAdapter
    private lateinit var viewBinding: FragmentAboutBinding

    override fun bindView(): View {
        viewBinding = FragmentAboutBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun lazyLoadData() {
        viewBinding.rvAbout.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = AboutAdapter(ctx, lists)
        viewBinding.rvAbout.addItemDecoration(
            RecycleViewDivider(activity, RecyclerView.HORIZONTAL)
        )
        viewBinding.rvAbout.adapter = adapter
    }

    override fun lazyLoadListener() {
        adapter.setOnItemClickListener { _, pos ->
            when (pos) {
                2 -> {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/stray-coding/Girl")
                    )
                    startActivity(urlIntent)
                }
            }
        }
    }
}