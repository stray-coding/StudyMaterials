package com.coding.studymaterials.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.databinding.FragmentArticleBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Coding.He
 * @date: 2020/7/2
 * @emil: 229101253@qq.com
 * @des:
 */
class ArticleFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentArticleBinding
    private val tabs = arrayListOf("Android", "IOS", "Flutter", "前端", "后端", "APP")
    override fun bindView(): View {
        viewBinding = FragmentArticleBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.viewpager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        viewBinding.viewpager2.adapter = object : FragmentStateAdapter(
            childFragmentManager,
            lifecycle
        ) {
            override fun getItemCount(): Int {
                return tabs.size
            }

            override fun createFragment(position: Int): Fragment {
                return PaperFragment.newInstance(tabs[position])
            }

        }


        val mediator = TabLayoutMediator(
            viewBinding.tabBar, viewBinding.viewpager2
        ) { tab, position ->
            tab.text = tabs[position]
        }.attach()


    }

    private val pageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

        }
    }

    override fun initListener() {
        viewBinding.viewpager2.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.viewpager2.unregisterOnPageChangeCallback(pageChangeCallback)
    }
}