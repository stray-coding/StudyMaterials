package com.coding.studymaterials.fragment

import android.os.Bundle
import android.view.View
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.databinding.FragmentPaperBinding

/**
 * @author: Coding.He
 * @date: 2021/2/20
 * @emil: 229101253@qq.com
 * @des:
 */
class PaperFragment private constructor() : BaseFragment() {
    companion object {
        fun newInstance(title: String): PaperFragment {
            val fragment = PaperFragment()
            fragment.title = title
            return fragment
        }
    }

    private var title: String = ""
    private lateinit var viewBinding: FragmentPaperBinding
    override fun bindView(): View {
        viewBinding = FragmentPaperBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.tvPaper.text = title
    }

    override fun initListener() {

    }
}