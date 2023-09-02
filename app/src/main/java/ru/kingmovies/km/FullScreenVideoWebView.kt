package ru.kingmovies.km

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout


class FullScreenVideoWebView : WebView {
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initView(context)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView(context: Context) {
        with(this.settings){
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            defaultTextEncodingName = "utf-8"
        }
        this.webChromeClient = chromeClientFullScreen(context)
    }

    inner class chromeClientFullScreen internal constructor(private val context: Context) :
        WebChromeClient() {
        var orientation = resources.configuration.orientation
        private var mCustomView: View? = null
        private var mCustomViewCallback: CustomViewCallback? = null
        private var mOriginalSystemUiVisibility = 0
        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomView == null) {
                null
            } else BitmapFactory.decodeResource(context.resources, 2130837573)
        }

        override fun onHideCustomView() {
            ((context as Activity).window.decorView as FrameLayout).removeView(mCustomView)
            mCustomView = null
            context.window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
            context.requestedOrientation = orientation
            mCustomViewCallback!!.onCustomViewHidden()
            mCustomViewCallback = null
        }

        override fun onShowCustomView(
            paramView: View,
            paramCustomViewCallback: CustomViewCallback
        ) {
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            mCustomView = paramView
            mOriginalSystemUiVisibility = (context as Activity).window.decorView.systemUiVisibility
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            mCustomViewCallback = paramCustomViewCallback
            (context.window.decorView as FrameLayout).addView(
                mCustomView,
                FrameLayout.LayoutParams(-1, -1)
            )
            context.window.decorView.systemUiVisibility = 3846 or SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}