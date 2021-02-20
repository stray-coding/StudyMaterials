package com.coding.studymaterials.activity

import android.os.Bundle
import android.view.View
import com.coding.girl.base.BaseFragment
import com.coding.studymaterials.R
import com.coding.studymaterials.base.BaseActivity
import com.coding.studymaterials.databinding.ActivityMainBinding
import com.coding.studymaterials.fragment.AboutFragment
import com.coding.studymaterials.fragment.GirlFragment
import com.coding.studymaterials.fragment.HomeFragment


class MainActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val girlFragment = GirlFragment()
    private val aboutFragment = AboutFragment()

    override fun bindView(): View {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initDataBeforeSetContentView(savedInstanceState: Bundle?) {

    }

    override fun initDataAfterSetContentView(savedInstanceState: Bundle?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.fl_main, homeFragment, "home")
            .add(R.id.fl_main, girlFragment, "girl")
            .add(R.id.fl_main, aboutFragment, "about")
            .commit()
        show(homeFragment)

    }

    override fun setListener() {
        viewBinding.bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bnv_home ->
                    show(homeFragment)
                R.id.bnv_girl ->
                    show(girlFragment)
                R.id.bnv_about ->
                    show(aboutFragment)
            }
            true
        }

        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_home)
            .setOnLongClickListener { true }
        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_girl)
            .setOnLongClickListener { true }
        viewBinding.bottomBar.getChildAt(0).findViewById<View>(R.id.bnv_about)
            .setOnLongClickListener { true }
    }

    private fun show(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(homeFragment)
            .hide(girlFragment)
            .hide(aboutFragment)
            .show(fragment)
            .commit()
    }
}