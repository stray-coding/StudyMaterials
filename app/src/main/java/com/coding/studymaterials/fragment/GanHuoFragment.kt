package com.coding.studymaterials.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coding.studymaterials.base.BaseFragment
import com.coding.studymaterials.databinding.FragmentGanhuoBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author: Coding.He
 * @date: 2020/7/2
 * @emil: 229101253@qq.com
 * @des:
 */
class GanHuoFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentGanhuoBinding
    private val tabs = arrayListOf("Android", "iOS", "Flutter", "前端", "后端", "APP")
    private val types = arrayListOf("Android", "iOS", "Flutter", "frontend", "backend", "app")
    override fun bindView(): View {
        viewBinding = FragmentGanhuoBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun lazyLoadData() {
        viewBinding.viewpager2.offscreenPageLimit = tabs.size
        viewBinding.viewpager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        viewBinding.viewpager2.adapter = object : FragmentStateAdapter(
            childFragmentManager,
            lifecycle
        ) {
            override fun getItemCount(): Int {
                return tabs.size
            }

            override fun createFragment(position: Int): Fragment {
                return PaperFragment.newInstance(types[position])
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

    override fun lazyLoadListener() {
        viewBinding.viewpager2.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.viewpager2.unregisterOnPageChangeCallback(pageChangeCallback)
    }
}