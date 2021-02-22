package com.coding.studymaterials.base

/**
 * @author: Coding.He
 * @date: 2020/7/3
 * @emil: stray-coding@foxmail.com
 * @des:
 */

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * Created by HDL on 2018/1/16.
 */
abstract class BaseFragment : Fragment() {
    private var mRootView: View? = null
    protected val TAG = this.javaClass.simpleName
    protected lateinit var ctx: Context
    private var isFirstLoad = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        Log.w(TAG, "onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.w(TAG, "onCreateView()")
        if (mRootView == null) {
            mRootView = bindView()
        }

        return mRootView!!
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume()")
        if (isFirstLoad) {
            isFirstLoad = false
            lazyLoadData()
            lazyLoadListener()
        }
    }

    protected fun showTip(msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun bindView(): View
    abstract fun lazyLoadData()
    abstract fun lazyLoadListener()
}