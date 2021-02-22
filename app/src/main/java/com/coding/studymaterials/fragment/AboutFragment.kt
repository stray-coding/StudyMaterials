package com.coding.studymaterials.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
 * @emil: stray-coding@foxmail.com
 * @des:
 */
class AboutFragment : BaseFragment() {
    companion object {
        private const val author = "stray-coding"
        private const val mail = "stray-coding@foxmail.com"
        private const val github = "https://github.com/stray-coding/StudyMaterials"
    }

    private val lists = listOf(
        "author：<font color=#1296db>$author</font>",
        "mail：<font color=#1296db> $mail </font>",
        "github：<font color=#1296db> $github </font>"
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
                0 -> {
                    val cm =
                        activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("label", author))
                }
                1 -> {
                    val cm =
                        activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("label", mail))
                }
                2 -> {
                    val cm =
                        activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("label", github))
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(github)
                    )
                    startActivity(urlIntent)
                }
            }
            showTip("已复制到剪贴板")
        }
    }
}