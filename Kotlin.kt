package kt.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.util.Log
import android.content.Context
import android.widget.Toast
import android.webkit.WebView
import android.webkit.WebSettings
import android.view.MenuItem
import android.util.AttributeSet
import android.annotation.SuppressLint
import android.webkit.WebViewClient
import kt.kotlin.activity.databinding.ActivityMainBinding
import com.itsaky.androidide.logsender.LogSender

public class Kotlin : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    private lateinit var webView: WebView
    
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        // Remove this line if you don't want AndroidIDE to show this app's logs
        LogSender.startLogging(this@Kotlin)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        // set content view to binding's root
        setContentView(binding.root)

      supportActionBar?.setDisplayHomeAsUpEnabled(true)


      // 获取WebView = 获取id
       webView = findViewById(R.id.webView)

      // 必要参数或者属性
        webView.webViewClient = WebViewClient()
      // 开启DOM Storage api以保证可以正常缓存一些数据
        webView.settings.domStorageEnabled = true

      // 设置缩放至屏幕的大小
        webView.settings.loadWithOverviewMode = true

      // 设置自适应屏幕，两者合用
        webView.settings.setPluginState(WebSettings.PluginState.OFF)

      // 将图片调整到适合WebView的大小
        webView.settings.setUseWideViewPort(true)

     // 设置true,才能让Webivew支持<meta>标签的viewport属性
        webView.settings.useWideViewPort = true

      // 设置缩放控件
        webView.settings.builtInZoomControls = true

      // 隐藏原生缩放控件
        webView.settings.displayZoomControls = false

      // 设置支持缩放
        webView.settings.setSupportZoom(true)

      // 开启 Application Caches 功能
        webView.settings.setAppCacheEnabled(true)

      // 设置  Application Caches 缓存目录
        webView.settings.setAppCachePath(cacheDir.absolutePath)

       // 设置可以访问文件
        webView.settings.setAllowFileAccess(true)

        // 加载URL
        webView.loadUrl("file:///android_asset/r.html")

        // 启用JavaScript
        webView.settings.javaScriptEnabled = true

        // 设置支持插件
        webView.settings.setJavaScriptEnabled(true)

       // 设置缓存模式为LOAD_CACHE_ELSE_NETWORK
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

      // 设置WebView不跳转浏览器
     webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })
}

    override fun onBackPressed() {
        // 检查是否有可回退的页面
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

       // 设置WebView返回上一页
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}