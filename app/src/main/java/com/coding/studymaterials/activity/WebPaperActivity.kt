package com.coding.studymaterials.activity

import android.os.Bundle
import android.view.View
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
            viewBinding.topBar.topBarTitle.text = intent.extras!!.getString("title", "GanHuo")
        }
    }

    override fun setListener() {

    }

}