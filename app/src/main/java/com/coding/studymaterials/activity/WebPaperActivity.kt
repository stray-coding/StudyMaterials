package com.coding.studymaterials.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coding.studymaterials.base.BaseActivity
import com.coding.studymaterials.databinding.ActivityWebPaperBinding

class WebPaperActivity : BaseActivity() {
    private lateinit var viewBinding: ActivityWebPaperBinding
    override fun bindView(): View {
        viewBinding = ActivityWebPaperBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initDataBeforeSetContentView(savedInstanceState: Bundle?) {

    }

    override fun initDataAfterSetContentView(savedInstanceState: Bundle?) {
        viewBinding.topBar.topBarLeft.setOnClickListener {
            finish()
        }
        val setting = viewBinding.wvPaper.settings
        setting.loadWithOverviewMode = true
        setting.useWideViewPort = true
        viewBinding.wvPaper.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                viewBinding.topBar.topBarTitle.text = title
            }
        }
        viewBinding.wvPaper.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        if (intent != null && intent.extras != null) {
            val id = intent.extras!!.getString("id", "")
            if (id.isNotEmpty()) {
                viewBinding.wvPaper.loadUrl("https://gank.io/post/$id")
            }
        }
    }

    override fun setListener() {

    }

    override fun onDestroy() {
        viewBinding.wvPaper.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            (parent as ViewGroup).removeView(viewBinding.wvPaper)
            destroy()
        }
        super.onDestroy()
    }
}