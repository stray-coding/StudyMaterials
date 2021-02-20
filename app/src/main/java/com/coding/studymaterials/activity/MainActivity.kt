package com.coding.studymaterials.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coding.studymaterials.R
import com.coding.studymaterials.base.BaseActivity
import com.coding.studymaterials.databinding.ActivityMainBinding
import com.coding.studymaterials.fragment.AboutFragment
import com.coding.studymaterials.fragment.GanHuoFragment
import com.coding.studymaterials.fragment.GirlFragment


class MainActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val fragments = arrayListOf(
        GanHuoFragment(),
        GirlFragment(),
        AboutFragment()
    )

    override fun bindView(): View {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initDataBeforeSetContentView(savedInstanceState: Bundle?) {

    }

    override fun initDataAfterSetContentView(savedInstanceState: Bundle?) {
        viewBinding.vpMain.isUserInputEnabled = false
        viewBinding.vpMain.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
    }

    override fun setListener() {
        viewBinding.bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bnv_ganhuo -> viewBinding.vpMain.currentItem = 0
                R.id.bnv_girl -> viewBinding.vpMain.currentItem = 1
                R.id.bnv_about -> viewBinding.vpMain.currentItem = 2
                else -> viewBinding.vpMain.currentItem = 0
            }
            true
        }
        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_ganhuo)
            .setOnLongClickListener { true }
        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_girl)
            .setOnLongClickListener { true }
        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_about)
            .setOnLongClickListener { true }
    }
}