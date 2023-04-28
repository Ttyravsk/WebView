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
import android.webkit.WebViewClient
import kt.kotlin.activity.databinding.ActivityMainBinding
import com.itsaky.androidide.logsender.LogSender

public class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    private lateinit var webView: WebView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Remove this line if you don't want AndroidIDE to show this app's logs
        LogSender.startLogging(this@MainActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        // set content view to binding's root
        setContentView(binding.root)

      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      // 获取WebView
        webView = findViewById(R.id.webView)

        // 加载URL
        webView.loadUrl("file:///android_asset/r.html")

        // 启用JavaScript
        webView.settings.javaScriptEnabled = true


      //  设置WebView不跳转浏览器
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}