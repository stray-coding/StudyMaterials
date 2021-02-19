package com.coding.studymaterials

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.coding.girl.base.BaseFragment
import com.coding.studymaterials.databinding.ActivityMainBinding
import com.coding.studymaterials.fragment.AboutFragment
import com.coding.studymaterials.fragment.GirlFragment
import com.coding.studymaterials.fragment.HomeFragment


class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val girlFragment = GirlFragment()
    private val aboutFragment = AboutFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val navHostFragment = NavHostFragment.create(R.navigation.nav_main)
        val transaction = supportFragmentManager.beginTransaction()

        transaction
//            .add(R.id.fl_main, homeFragment, "home")
//            .add(R.id.fl_main, girlFragment, "girl")
//            .add(R.id.fl_main, aboutFragment, "about")
            .add(R.id.fl_main, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commit()
        show(homeFragment)
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