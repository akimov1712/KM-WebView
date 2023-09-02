package ru.kingmovies.km

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.example.km.R

class MainActivity : AppCompatActivity() {

    private val URL = "https://kmovies.ru/"

    private val webView by lazy{
        findViewById<FullScreenVideoWebView>(R.id.webView)
    }
    private val progressBar by lazy{
        findViewById<ProgressBar>(R.id.progressBar)
    }
    private val ivLogo by lazy{
        findViewById<ImageView>(R.id.iv_logo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView.loadUrl(URL)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                ivLogo.visibility = View.GONE
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url: String = request?.url.toString();
                view?.loadUrl(url)
                return true
            }
            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                webView.loadUrl(url)
                return true
            }
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        webView.resumeTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}